import com.newland.balbaxmx.layered.simple.common.OrikaBeanUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhangyh
 * @ClassName: OrikaTest
 * @Date: 2020/12/9 10:59
 * @Operation:
 * @Description: ${description}
 */
public class OrikaTest {

    @Test
    public void test1(){
        List<AddressVo> vos = Arrays.asList(
                new AddressVo("福州",222),
                new AddressVo("河北",111));
        User1 user = new User1("张三",12,1.0,new Address("北京",999),vos,"男"
        ,"185");
        UserVo vo = OrikaBeanUtils.copyBean(user,UserVo.class);
        System.out.println(vo.toString());
    }


    @Test
    public void test2()throws Exception{
        List<AddressVo> vos = Arrays.asList(
                new AddressVo("福州",222),
                new AddressVo("河北",111));
        User1 user = new User1("张三",12,1.0,new Address("北京",999),vos,
                "女","184");
        UserVo vo = OrikaBeanUtils.copyBean(user,UserVo.class,"sex","gender","phone","number");
        System.out.println(vo.toString());
    }


    @Test
    public void test3()throws Exception{
        List<AddressVo> vos = new ArrayList<>();
        vos.add(new AddressVo("福州",222,"男"));
        vos.add(new AddressVo("漳州",3333,"女"));
        List<Address> vo = OrikaBeanUtils.copyList(vos,AddressVo.class,Address.class,true,"gender","sex");
        System.out.println(vo.toString());
    }
}
