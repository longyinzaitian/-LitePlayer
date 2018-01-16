package org.loader.liteplayer.network;

import java.io.File;

/**
 * @author longyinzaitian
 * @date 2018/1/8
 */

public interface DownFileCallback {
    /**
     * 进度
     * @param pro int
     */
    void updateProgress(int pro);

    /**
     * 返回
     * @param file  file
     */
    void onResponseFile(File file);
}
