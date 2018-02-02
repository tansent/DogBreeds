package jtli.com.dogbreeds.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.bean.Model;
import jtli.com.dogbreeds.utils.GlideUtils;

/**
 * Created by Jingtian(Tansent).
 */

public class DogBreedsAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

    public DogBreedsAdapter(List<Model> data) {
        super(R.layout.item_dog, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Model item) {
        helper.setText(R.id.tv_dog_breed, item.getTitle());
        if (item.getImage1()!=null&&item.getImage1().length()>0){
            GlideUtils.load(mContext,item.getImage1(), (ImageView) helper.getView(R.id.iv_dog1));
        }
        if (item.getImage2()!=null&&item.getImage2().length()>0){
            GlideUtils.load(mContext,item.getImage2(), (ImageView) helper.getView(R.id.iv_dog2));
        }
        if (item.getImage3()!=null&&item.getImage3().length()>0){
            GlideUtils.load(mContext,item.getImage3(), (ImageView) helper.getView(R.id.iv_dog3));
        }
        TextView tvMorePhoto= helper.getView(R.id.tv_more_photo);
        tvMorePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(item.getTitle(), item.getImage2(), helper.getView(R.id.iv_dog2));
            }
        });
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(String title, String img, View view);}

}
