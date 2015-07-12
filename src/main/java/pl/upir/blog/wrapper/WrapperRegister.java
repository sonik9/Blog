package pl.upir.blog.wrapper;

import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;

/**
 * Created by Vitalii on 09.07.2015.
 */
public class WrapperRegister {

    BlgUser blgUser;
    BlgUserDetail blgUserDetail;

    public BlgUser getBlgUser() {
        return blgUser;
    }

    public void setBlgUser(BlgUser blgUser) {
        this.blgUser = blgUser;
    }

    public BlgUserDetail getBlgUserDetail() {
        return blgUserDetail;
    }

    public void setBlgUserDetail(BlgUserDetail blgUserDetail) {
        this.blgUserDetail = blgUserDetail;
    }
}
