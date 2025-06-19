package cn.valuetodays.api2.module.fortune.channel.haitong;

import cn.valuetodays.api2.module.fortune.channel.StockHoldersInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-04-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HaitongStockHoldersInfo extends StockHoldersInfo {
    private Double holdVolumeDouble;

    @Override
    public void initValues() {
        super.setHoldVolume(holdVolumeDouble.intValue());
    }

}
