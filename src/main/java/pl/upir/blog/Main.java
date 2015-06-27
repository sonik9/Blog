package pl.upir.blog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.service.BlgUserService;

/**
 * Created by Vitalii on 23.06.2015.
 */
public class Main {
    public static void main(String[] args){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/root-context.xml");
        BlgUserService blgUserService = ctx.getBean("blgUserService",BlgUserService.class);

        BlgUser blgUser = blgUserService.findById(1);
        blgUser.setUsrLogin("Vitalii");
        blgUserService.save(blgUser);
    }
}
