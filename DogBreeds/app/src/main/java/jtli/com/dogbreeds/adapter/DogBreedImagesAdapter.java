package jtli.com.dogbreeds.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.bean.Model;
import jtli.com.dogbreeds.utils.DensityUtil;
import jtli.com.dogbreeds.utils.GlideUtils;

/**
 * Created by Jingtian(Tansent).
 */

public class DogBreedImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DogBreedImagesAdapter(List<String> data) {
        super(R.layout.item_dog_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String imgUrl) {
        if (helper.getLayoutPosition() % 2 == 0) { //.getPosition()
            DensityUtil.setViewMargin(helper.itemView, false, 10, 0, 0, 40);
        } else {
            DensityUtil.setViewMargin(helper.itemView, false, 8, 0, 0, 40);
        }
        GlideUtils.load(mContext,imgUrl, (ImageView) helper.getView(R.id.iv_item_top_news));
    }
}
