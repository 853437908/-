package cn.tedu.straw.portal.co;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R <T> {
    private  Integer state;
    private  String message;
    private T data;

    public static <C> R ok(C data){
        return new R<C>().setState(State.OK).setData(data);
    }

    public static R ok() {
        return new R().setState(State.OK);
    }

    public static R failure(Integer state, String message) {
        return new R().setState(state).setMessage(message);
    }

    public static R failure(Integer state, Throwable e) {
        return failure(state, e.getMessage());
    }

    public static interface State{
        int OK=2000;
        int ERR_PARAMETER_INVALIDATION = 4000;
        int ERR_INVITE_CODE=4001;
        int ERR_CLASS_DISABLED=4002;
        int ERR_PHONE_DUPLICATE=4003;
        int ERR_INSERT=4004;
        int ERR_ACCESS_DENIED=5000;
        int ERR_FILEEMPTY=6000;
        int ERR_FILESIZE=6001;
        int ERR_FILETYPE=6002;
        int ERR_FILEIO=6003;
        int ERR_COMMENT_NOT=6004;
        int ERR_PERMISSION=6005;
        int ERR_UPDATE=6006;
        int ERR_DELETE=6006;
        int ERR_QUESTION_NOT_NULL=7000;
        int ERR_UNKNOWN=9999;

    }



}
