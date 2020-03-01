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

	String OSS_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
	String OSS_ACCESSKEY_ID = "LTAI4FgmBGcAdgVjL8Gn2Rw9";
	String OSS_ACCESSKEY_SECRET = "wwi6ePETPvo0kaUnLH84TyQsk12RW7";
	String OSS_BUCKET_NAME = "yanluo-garbage-sort-b1";
	String OSS_BUCKET_DOMAIN = "yanluo-garbage-sort-b1.oss-cn-shenzhen.aliyuncs.com";
}
