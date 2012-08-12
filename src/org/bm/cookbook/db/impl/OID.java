package org.bm.cookbook.db.impl;

import org.bm.cookbook.db.IOID;

public class OID implements IOID {

	private final long oid;

	public OID(long oid) {
		this.oid = oid;
	}

	@Override
	public long getOID() {
		return oid;
	}
	
	@Override
	public boolean isNull() {
		return 0 == oid;
	}

	@Override
	public String toString() {
		return Long.toString(oid);
	}
	
}
