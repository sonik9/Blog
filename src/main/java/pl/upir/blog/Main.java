package pl.upir.blog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.service.BlgUserDetailService;
import pl.upir.blog.service.BlgUserService;

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
                "FirstName:"+blgUser.getBlgUserDetail().getUsrDetFirstname()+"\n"+
                "LastName:"+blgUser.getBlgUserDetail().getUsrDetLastname()+"\n"+
                "Role:");
        for(BlgDicRole blgDicRole: blgUser.getBlgUserRoleSet()){
            System.out.print(blgDicRole.getRoleName()+" ");
        }

        //BlgUserDetail blgUserDetail = blgUserDetailService.findByUsrId(1);
        //System.out.print(blgUserDetail.getUsrDetFirstname()+" "+blgUserDetail.getUsrDetLastname());



        //blgUser.setUsrLogin("Vitalii");
        //blgUserService.save(blgUser);
    }
}
