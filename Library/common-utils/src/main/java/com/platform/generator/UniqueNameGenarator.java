package com.platform.generator;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Muhil 
 */
public class UniqueNameGenarator implements IdentifierGenerator {

    private static final long serialVersionUID = -8221204836125475441L;

	@Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return object.getClass().getSimpleName() + UUID.randomUUID();
    }
	
}