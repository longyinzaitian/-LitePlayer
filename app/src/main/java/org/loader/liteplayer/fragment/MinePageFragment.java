package org.loader.liteplayer.fragment;

/**
 * @author 15361
 * @date 2017/12/24.
 */
public class MinePageFragment extends BaseFragment {
    private static MinePageFragment instance;

    public static MinePageFragment getInstance(){
        if (instance == null){
            instance = new MinePageFragment();
        }

        return instance;
    }
}
