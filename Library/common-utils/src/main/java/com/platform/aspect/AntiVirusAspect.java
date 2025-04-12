package com.platform.aspect;

import java.io.IOException;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.platform.antivirus.ClamAVService;
import com.platform.antivirus.VirusScanResult;
import com.platform.antivirus.VirusScanStatus;
import com.platform.exceptions.VirusScanException;
import com.platform.logging.Log;


/**
 * @author Muhil
 * perform virus scan on multipart file on all controller endpoints.
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(value = "antivirus.clamav.enabled", havingValue = "true")
public class AntiVirusAspect {
	
	@Autowired
	private ClamAVService clamAvService;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	protected void endPointspointCut() {

	}

	// TODO: change to before
	@Around(value = "endPointspointCut()")
	public Object endPointspointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] arguments = joinPoint.getArgs();
		for (Object argument : arguments) {
			if (argument != null) {
				if (argument instanceof MultipartFile file) {
					performScan(file);
				} else if (argument instanceof List<?> argFiles) {
					List<MultipartFile> files = argFiles.stream().filter(file -> file instanceof MultipartFile)
							.map(file -> (MultipartFile) file).toList();
					for (MultipartFile file : files) {
						performScan(file);
					}
				}
			}
		}
		return joinPoint.proceed();
	}

	private void performScan(MultipartFile file) throws IOException, VirusScanException {
		Log.platform.info("Performing virus scan for file {}", file.getOriginalFilename());
		VirusScanResult result = clamAvService.scan(file.getInputStream());
		Log.platform.info("File {} scanning is successful : {}", file.getOriginalFilename(), result);
		if (result.getStatus() != VirusScanStatus.PASSED) {
			Log.platform.info("File scan failed with error {}", result.getStatus());
			//TODO: audit log
			throw new VirusScanException(String.format("File %s may be corrupt or infected with malicious contents!",
					file.getOriginalFilename()));
		}
	}

}