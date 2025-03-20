package com.platform.storage;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Muhil
 * values are mapped with sql PK, any new storage type added should be updated here!
 */
public enum StoreType {
	
	GCS(1), NFS(2), AWS(3), AZURE(4), INTERNAL(5);
	
	int value;
	
	StoreType (int val){
		this.value = val;
	}
	
	public int getValue(){
		return value;
	}
	
	public static Stream<StoreType> stream() {
		return Stream.of(StoreType.values());
	}

	public static Optional<StoreType> findType(String name) {
		return StoreType.stream().filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
	}
	
	public static Optional<StoreType> findTypeById (int id) {
		return StoreType.stream().filter(type -> type.getValue() == id).findFirst();
	}

}
