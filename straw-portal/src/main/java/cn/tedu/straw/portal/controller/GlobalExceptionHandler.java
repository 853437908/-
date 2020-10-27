package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.co.R;
import cn.tedu.straw.portal.service.ex.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler
    public R handleException(Throwable e){
        if(e instanceof InviteCodeException){
            return R.failure(R.State.ERR_INVITE_CODE,e);
        }else if(e instanceof ParameterValidationException){
            return R.failure(R.State.ERR_PARAMETER_INVALIDATION, e);
        } else if(e instanceof ClassDisabledException){
            return R.failure(R.State.ERR_CLASS_DISABLED,e);
        } else if(e instanceof PhoneDuplicateException){
            return R.failure(R.State.ERR_PHONE_DUPLICATE,e);
        } else if(e instanceof InsertException){
            return R.failure(R.State.ERR_INSERT,e);
        }else if(e instanceof AccessDeniedException){
            return  R.failure(R.State.ERR_ACCESS_DENIED,e );
        }else if(e instanceof FileEmptyException){
            return  R.failure(R.State.ERR_FILEEMPTY,e);
        }else if(e instanceof FileSizeException){
            return  R.failure(R.State.ERR_FILESIZE,e);
        }else if(e instanceof FileTypeException){
            return  R.failure(R.State.ERR_FILETYPE,e);
        }else if(e instanceof FileIOException){
            return  R.failure(R.State.ERR_FILEIO,e);
        }else if(e instanceof QuestionNotFoundException){
            return  R.failure(R.State.ERR_QUESTION_NOT_NULL,e);
        }else if(e instanceof CommentNotFoundException){
            return  R.failure(R.State.ERR_COMMENT_NOT,e);
        }else if(e instanceof PermissionDeniedException){
            return  R.failure(R.State.ERR_PERMISSION,e);
        }else if(e instanceof DeleteException){
            return  R.failure(R.State.ERR_DELETE,e);
        }else if(e instanceof UpdateException){
            return  R.failure(R.State.ERR_UPDATE,e);
        }else {
            return R.failure(R.State.ERR_UNKNOWN,e);
        }

    }



}
