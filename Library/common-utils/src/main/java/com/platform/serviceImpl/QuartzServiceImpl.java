package com.platform.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.entity.QuartzJobHistory;
import com.platform.entity.QuartzJobInfo;
import com.platform.repository.QuartzJobHistoryRepository;
import com.platform.repository.QuartzJobInfoRepository;
import com.platform.server.BaseSession;
import com.platform.service.BaseService;

/**
 * @author Muhil
 */
@Service
public class QuartzServiceImpl implements BaseService {
	
	@Autowired
	private QuartzJobInfoRepository repo;
	
	@Autowired
	private QuartzJobHistoryRepository his;

	@Override
	public BaseEntity findById(Long rootId) {
		return repo.findById(rootId).get();
	}
	
	public QuartzJobInfo createQuartzJobInfo(String name, String group, boolean isRecurring) {
		QuartzJobInfo info = new QuartzJobInfo();
		info.setIsrecurring(isRecurring);
		info.setJobname(name);
		info.setJobgroup(group);
		info.setTenantId(BaseSession.getTenantId());
		return repo.save(info);
	}
	
	public QuartzJobHistory createOrUpdateQuartzHistory(String uuid, String name, String group, String status, String errorMsg) {
		QuartzJobHistory history = his.findJobHistory(uuid);
		if (history == null) {
			history = new QuartzJobHistory();
			QuartzJobInfo info = repo.findJob(group, name);
			history.setJobinfoid(info.getRootid());
			history.setUuid(uuid);
		}
		history.setErrorinfo(errorMsg);
		history.setJobstatus(status);
		return his.save(history);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
