package com.xiongms.libcore.updateversion.callback;


public interface BaseCallback {
    /**
     * 开始检查更新或开始下载更新
     */
    void checkUpdateStart();

    /**
     * 检查更新失败或下载更新失败
     *
     * @param t 把Throwable返回，并且内部也打印了log，方便开发时调试，
     */
    void checkUpdateFailure(Throwable t);

    /**
     * 检查更新结束或下载更新结束
     */
    void checkUpdateFinish();

}
