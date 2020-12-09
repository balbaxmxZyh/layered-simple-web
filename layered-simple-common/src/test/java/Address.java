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
public class Address {
    private String addName;
    private Integer num;

    private String sex;

    public Address(String addName, Integer num) {
        this.addName = addName;
        this.num = num;
    }
}
