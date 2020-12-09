import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: zhangyh
 * @ClassName: Address
 * @Date: 2020/12/9 11:07
 * @Operation:
 * @Description: ${description}
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressVo {
    private String addName;
    private Integer num;
    private String gender;

    public AddressVo(String addName, Integer num) {
        this.addName = addName;
        this.num = num;
    }
}
