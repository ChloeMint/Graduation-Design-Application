package com.example.greenplant.entities

class PlantRecommend (val cityType:String, val environment:String, val plantGrow:String)

fun getPlantRecommend(lng:Double, lat:Double):PlantRecommend{
    if (lng in 103.0..135.0 && lat in 33.0..53.0){
        return PlantRecommend("北方城市", "气候寒冷干燥，冬季漫长且寒冷，夏季短暂而炎热。土壤多为碱性或中性。","榆叶梅、西府海棠、国槐、侧柏、白皮松等。这些植物耐寒、耐旱，对土壤要求不严，适合在北方城市生长。")
    }else if (lng in 100.0..123.0 && lat in 22.0..34.0){
        return PlantRecommend("南方城市", "气候温暖湿润，四季分明，雨水充沛。土壤多为酸性或微酸性。","茶花、杜鹃、桂花、榕树、棕榈等。这些植物喜温暖湿润的环境，适合在南方城市生长。")
    }else if (lng in 108.0..125.0 && lat in 20.0..42.0){
        return PlantRecommend("沿海城市", "气候湿润，多风多雨，盐分含量高。土壤可能偏盐碱。","红树、木麻黄、海桐等。这些植物具有较强的抗风、抗盐能力，适合在沿海城市生长。")
    }else if (lng in 91.0..108.0 && lat in 24.0..31.0){
        return PlantRecommend("高原城市", "气候寒冷干燥，紫外线强烈，氧气稀薄。土壤多为高山草甸土或砾石土","高山杜鹃、藏报春、青海云杉等。这些植物适应高山环境，耐寒、耐旱，能在高原城市生长良好。")
    }
    return  PlantRecommend("未知", "无", "无")
}