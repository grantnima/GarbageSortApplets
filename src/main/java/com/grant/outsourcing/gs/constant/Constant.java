package com.grant.outsourcing.gs.constant;

import com.grant.outsourcing.gs.db.mapper.SystemSettingMapper;

public interface Constant
{
	interface GarbageSort {
		int Recyclable = 1;
		int Kitchen = 2;
		int Harmful = 3;
		int Others = 4;
		int NormalLiquid = 5;
	}
}
