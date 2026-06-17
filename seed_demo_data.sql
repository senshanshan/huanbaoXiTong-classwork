USE nep;

START TRANSACTION;

INSERT INTO grid_province (province_name, province_abbr)
SELECT '河北省', '冀' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '河北省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '山西省', '晋' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '山西省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '内蒙古自治区', '蒙' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '内蒙古自治区');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '吉林省', '吉' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '吉林省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '黑龙江省', '黑' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '黑龙江省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '上海市', '沪' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '上海市');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '江苏省', '苏' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '江苏省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '浙江省', '浙' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '浙江省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '安徽省', '皖' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '安徽省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '福建省', '闽' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '福建省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '江西省', '赣' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '江西省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '山东省', '鲁' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '山东省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '河南省', '豫' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '河南省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '湖北省', '鄂' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '湖北省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '湖南省', '湘' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '湖南省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '广东省', '粤' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '广东省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '广西壮族自治区', '桂' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '广西壮族自治区');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '海南省', '琼' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '海南省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '四川省', '川' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '四川省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '贵州省', '黔' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '贵州省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '云南省', '云' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '云南省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '西藏自治区', '藏' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '西藏自治区');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '陕西省', '陕' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '陕西省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '甘肃省', '甘' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '甘肃省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '青海省', '青' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '青海省');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '宁夏回族自治区', '宁' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '宁夏回族自治区');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '新疆维吾尔自治区', '新' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '新疆维吾尔自治区');
INSERT INTO grid_province (province_name, province_abbr)
SELECT '天津市', '津' WHERE NOT EXISTS (SELECT 1 FROM grid_province WHERE province_name = '天津市');

INSERT INTO grid_city (city_name, province_id)
SELECT '石家庄市', province_id FROM grid_province p WHERE p.province_name = '河北省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '石家庄市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '唐山市', province_id FROM grid_province p WHERE p.province_name = '河北省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '唐山市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '太原市', province_id FROM grid_province p WHERE p.province_name = '山西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '太原市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '大同市', province_id FROM grid_province p WHERE p.province_name = '山西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '大同市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '呼和浩特市', province_id FROM grid_province p WHERE p.province_name = '内蒙古自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '呼和浩特市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '包头市', province_id FROM grid_province p WHERE p.province_name = '内蒙古自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '包头市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '长春市', province_id FROM grid_province p WHERE p.province_name = '吉林省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '长春市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '吉林市', province_id FROM grid_province p WHERE p.province_name = '吉林省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '吉林市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '哈尔滨市', province_id FROM grid_province p WHERE p.province_name = '黑龙江省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '哈尔滨市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '大庆市', province_id FROM grid_province p WHERE p.province_name = '黑龙江省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '大庆市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '浦东新区', province_id FROM grid_province p WHERE p.province_name = '上海市' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '浦东新区' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '南京市', province_id FROM grid_province p WHERE p.province_name = '江苏省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '南京市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '苏州市', province_id FROM grid_province p WHERE p.province_name = '江苏省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '苏州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '杭州市', province_id FROM grid_province p WHERE p.province_name = '浙江省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '杭州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '宁波市', province_id FROM grid_province p WHERE p.province_name = '浙江省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '宁波市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '合肥市', province_id FROM grid_province p WHERE p.province_name = '安徽省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '合肥市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '芜湖市', province_id FROM grid_province p WHERE p.province_name = '安徽省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '芜湖市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '福州市', province_id FROM grid_province p WHERE p.province_name = '福建省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '福州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '厦门市', province_id FROM grid_province p WHERE p.province_name = '福建省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '厦门市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '南昌市', province_id FROM grid_province p WHERE p.province_name = '江西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '南昌市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '赣州市', province_id FROM grid_province p WHERE p.province_name = '江西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '赣州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '济南市', province_id FROM grid_province p WHERE p.province_name = '山东省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '济南市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '青岛市', province_id FROM grid_province p WHERE p.province_name = '山东省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '青岛市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '郑州市', province_id FROM grid_province p WHERE p.province_name = '河南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '郑州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '洛阳市', province_id FROM grid_province p WHERE p.province_name = '河南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '洛阳市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '武汉市', province_id FROM grid_province p WHERE p.province_name = '湖北省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '武汉市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '宜昌市', province_id FROM grid_province p WHERE p.province_name = '湖北省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '宜昌市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '长沙市', province_id FROM grid_province p WHERE p.province_name = '湖南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '长沙市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '株洲市', province_id FROM grid_province p WHERE p.province_name = '湖南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '株洲市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '广州市', province_id FROM grid_province p WHERE p.province_name = '广东省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '广州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '深圳市', province_id FROM grid_province p WHERE p.province_name = '广东省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '深圳市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '南宁市', province_id FROM grid_province p WHERE p.province_name = '广西壮族自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '南宁市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '桂林市', province_id FROM grid_province p WHERE p.province_name = '广西壮族自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '桂林市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '海口市', province_id FROM grid_province p WHERE p.province_name = '海南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '海口市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '三亚市', province_id FROM grid_province p WHERE p.province_name = '海南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '三亚市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '成都市', province_id FROM grid_province p WHERE p.province_name = '四川省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '成都市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '绵阳市', province_id FROM grid_province p WHERE p.province_name = '四川省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '绵阳市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '贵阳市', province_id FROM grid_province p WHERE p.province_name = '贵州省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '贵阳市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '遵义市', province_id FROM grid_province p WHERE p.province_name = '贵州省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '遵义市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '昆明市', province_id FROM grid_province p WHERE p.province_name = '云南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '昆明市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '大理市', province_id FROM grid_province p WHERE p.province_name = '云南省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '大理市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '拉萨市', province_id FROM grid_province p WHERE p.province_name = '西藏自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '拉萨市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '西安市', province_id FROM grid_province p WHERE p.province_name = '陕西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '西安市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '宝鸡市', province_id FROM grid_province p WHERE p.province_name = '陕西省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '宝鸡市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '兰州市', province_id FROM grid_province p WHERE p.province_name = '甘肃省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '兰州市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '酒泉市', province_id FROM grid_province p WHERE p.province_name = '甘肃省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '酒泉市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '西宁市', province_id FROM grid_province p WHERE p.province_name = '青海省' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '西宁市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '银川市', province_id FROM grid_province p WHERE p.province_name = '宁夏回族自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '银川市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '乌鲁木齐市', province_id FROM grid_province p WHERE p.province_name = '新疆维吾尔自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '乌鲁木齐市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '喀什市', province_id FROM grid_province p WHERE p.province_name = '新疆维吾尔自治区' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '喀什市' AND c.province_id = p.province_id);
INSERT INTO grid_city (city_name, province_id)
SELECT '和平区', province_id FROM grid_province p WHERE p.province_name = '天津市' AND NOT EXISTS (SELECT 1 FROM grid_city c WHERE c.city_name = '和平区' AND c.province_id = p.province_id);

INSERT INTO grid_member (gm_name, gm_code, password, province_id, city_id, tel, state, remarks)
SELECT CONCAT(c.city_name, '网格员'), CONCAT('demo', LPAD(c.city_id, 3, '0')), '123456', c.province_id, c.city_id,
       CONCAT('139', LPAD(c.city_id, 8, '0')), 1, '演示数据网格员'
FROM grid_city c
WHERE NOT EXISTS (
    SELECT 1 FROM grid_member gm WHERE gm.gm_code = CONCAT('demo', LPAD(c.city_id, 3, '0'))
)
AND NOT EXISTS (
    SELECT 1 FROM grid_member gm WHERE gm.province_id = c.province_id AND gm.city_id = c.city_id AND gm.state = 1
);

INSERT INTO aqi_feedback (
    tel_id, province_id, city_id, address, information, estimated_grade,
    af_date, af_time, gm_id, assign_date, assign_time, state, remarks
)
SELECT '13800000000',
       c.province_id,
       c.city_id,
       CONCAT(c.city_name, '生态监测示范点'),
       CASE MOD(c.city_id, 4)
           WHEN 0 THEN '发现工业园区附近有刺激性气味'
           WHEN 1 THEN '道路扬尘明显，空气能见度下降'
           WHEN 2 THEN '居民区附近疑似焚烧产生烟尘'
           ELSE '施工区域粉尘较多，需要现场核查'
       END,
       MOD(c.city_id, 6) + 1,
       DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL MOD(c.city_id, 14) DAY), '%Y-%m-%d'),
       CONCAT(LPAD(8 + MOD(c.city_id, 10), 2, '0'), ':', LPAD(10 + MOD(c.city_id, 45), 2, '0'), ':00'),
       gm.gm_id,
       DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL MOD(c.city_id, 10) DAY), '%Y-%m-%d'),
       CONCAT(LPAD(9 + MOD(c.city_id, 8), 2, '0'), ':', LPAD(20 + MOD(c.city_id, 35), 2, '0'), ':00'),
       2,
       'demo-seed'
FROM grid_city c
JOIN (
    SELECT province_id, city_id, MIN(gm_id) AS gm_id
    FROM grid_member
    WHERE state = 1
    GROUP BY province_id, city_id
) gm ON gm.province_id = c.province_id AND gm.city_id = c.city_id
WHERE NOT EXISTS (
    SELECT 1 FROM aqi_feedback af
    WHERE af.remarks = 'demo-seed' AND af.province_id = c.province_id AND af.city_id = c.city_id
);

INSERT INTO statistics (
    province_id, city_id, address, so2_value, so2_level, co_value, co_level,
    spm_value, spm_level, aqi_id, confirm_date, confirm_time, gm_id, fd_id, information, remarks
)
SELECT c.province_id,
       c.city_id,
       CONCAT(c.city_name, '生态监测示范点'),
       40 + c.city_id * 7,
       LEAST(6, MOD(c.city_id, 6) + 1),
       8 + c.city_id * 3,
       LEAST(6, MOD(c.city_id + 1, 6) + 1),
       35 + c.city_id * 5,
       LEAST(6, MOD(c.city_id + 2, 6) + 1),
       MOD(c.city_id, 6) + 1,
       DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL MOD(c.city_id, 180) DAY), '%Y-%m-%d'),
       CONCAT(LPAD(10 + MOD(c.city_id, 8), 2, '0'), ':', LPAD(15 + MOD(c.city_id, 40), 2, '0'), ':00'),
       gm.gm_id,
       '13800000000',
       CASE MOD(c.city_id, 4)
           WHEN 0 THEN '已完成现场核查，建议加强园区排放巡检'
           WHEN 1 THEN '已洒水降尘，后续继续跟踪道路扬尘'
           WHEN 2 THEN '已排查疑似焚烧点并完成劝导'
           ELSE '已要求施工方增加围挡和喷淋'
       END,
       'demo-seed'
FROM grid_city c
JOIN (
    SELECT province_id, city_id, MIN(gm_id) AS gm_id
    FROM grid_member
    WHERE state = 1
    GROUP BY province_id, city_id
) gm ON gm.province_id = c.province_id AND gm.city_id = c.city_id
WHERE NOT EXISTS (
    SELECT 1 FROM statistics st
    WHERE st.remarks = 'demo-seed' AND st.province_id = c.province_id AND st.city_id = c.city_id
);

COMMIT;

SELECT COUNT(*) AS province_count FROM grid_province;
SELECT COUNT(*) AS city_count FROM grid_city;
SELECT COUNT(*) AS member_count FROM grid_member;
SELECT COUNT(*) AS feedback_count FROM aqi_feedback;
SELECT COUNT(*) AS statistics_count FROM statistics;
