import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: zhangyh
 * @ClassName: User1
 * @Date: 2020/12/9 11:04
 * @Operation:
 * @Description: ${description}
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User1 {

    private String name;
    private Integer age;
    private double account;
    private Address address;
    private List<AddressVo> addresss;

    private String sex;

    private String phone;


}
