package pl.upir.blog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.entity.BlgUserRole;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;

import java.util.function.Consumer;

/**
 * Created by Vitalii on 23.06.2015.
 */
public class Main {
    public static void main(String[] args){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/root-context.xml");
        BlgUserService blgUserService = ctx.getBean("blgUserService",BlgUserService.class);
        BlgUserDetailService blgUserDetailService = ctx.getBean("blgUserDetailService", BlgUserDetailService.class);


        /*BlgUserDetail blgUserDetail = blgUserDetailService.findById(1);
        System.out.println("Login:"+blgUserDetail.getBlgUser().getUsrLogin()+"\n"+
        "Password:"+blgUserDetail.getBlgUser().getUsrPassword()+"\n"+
        "TimeChanged:"+blgUserDetail.getBlgUser().getUsrDateTimeChange()+"\n"+
        "FirstName:"+blgUserDetail.getUsrDetFirstname()+"\n"+
        "LastName:"+blgUserDetail.getUsrDetLastname());*/
        BlgUser blgUser = blgUserService.findById(1);
        System.out.println("Login:"+blgUser.getUsrLogin()+"\n"+
                "Password:"+blgUser.getUsrPassword()+"\n"+
                "TimeChanged:"+blgUser.getUsrDateTimeChange()+"\n"+
                "FirstName:"+blgUser.getGetBlgUserDetail().getUsrDetFirstname()+"\n"+
                "LastName:"+blgUser.getGetBlgUserDetail().getUsrDetLastname()+"\n"+
                "Role:");
        for(BlgDicRole blgDicRole: blgUser.getBlgUserRoleSet()){
            System.out.print(blgDicRole.getRoleName()+" ");
        }



        //blgUser.setUsrLogin("Vitalii");
        //blgUserService.save(blgUser);
    }
}
