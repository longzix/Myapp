package cc.mrbird.febs.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @author longzx
 */
@Data
@TableName("m_que_table")
@Excel("试题表")
public class Que implements Serializable {



    /**
     * 试题 ID
     */
    @TableId(value = "QUE_ID", type = IdType.AUTO)
    private Long queId;

    /**
     * 题干
     */
    @TableField("QUE_TITLE")
    @Size(min = 4, max = 10, message = "{range}")

    private String queTitle;

    /**
     * 选项A
     */
    @TableField("QUE_A")
    private String queA;
    /**
     * 选项B
     */
    @TableField("QUE_B")
    private String queB;
    /**
     * 选项C
     */
    @TableField("QUE_C")
    private String queC;
    /**
     * 选项A
     */
    @TableField("QUE_D")
    private String queD;




    public Long getId() {
        return queId;
    }
}
