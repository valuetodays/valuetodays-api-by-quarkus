package cn.valuetodays.module.codegenerator.advanced.config.configfile;

import cn.valuetodays.module.codegenerator.util.StringExUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lei.liu
 * @since 2018-09-06 17:51:01
 */
@Getter
@Setter
public class Project {
    private boolean debug;
    private String name;
    private String formattedName;
    private String targetBaseDir;
    private boolean zip = true;


    public synchronized String getFormattedName() {
        if (StringExUtil.isNotBlank(formattedName)) {
            return formattedName;
        }
        if (debug) {
            formattedName = name + "-debug-" + System.currentTimeMillis();
        } else {
            formattedName = name + "-" + System.currentTimeMillis();
        }
        return formattedName;
    }

}
