package com.cygrove.libcore.updateversion.task;


import com.cygrove.libcore.updateversion.request.IRequest;
import com.cygrove.libcore.updateversion.request.PostRequest;

import java.util.Map;

public class PostTask extends BaseTask<PostTask> {
    public PostTask(String url) {
        super(url);
    }

    public PostTask(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    public void execute() {
        IRequest request = new PostRequest(mUrl, mParams, mCallback);
        request.request();
    }
}
