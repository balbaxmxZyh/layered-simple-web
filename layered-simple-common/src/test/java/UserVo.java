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
public class UserVo {

    private String name;
    private Integer age;
    private double account;
    private Address address;
    private List<Address> addresss;

    private String gender;

    private Integer number;

}
