package com.example.samplemodule.flowlayoutsample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samplemodule.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yellow on 2017/9/20.
 */

public class PropertyDialog extends Dialog implements View.OnClickListener {


    private Context context;
    private LoveLunchChooseGoodsMode mode;

    private LinearLayout ll_main_layout;
    private TextView tv_goods_name;
    private TextView tv_price;
    private TextView tv_num;
    private TextView tv_sure;
    private ImageView iv_decrease;
    private ImageView iv_close;
    private ImageView iv_increase;
    private List<View> itemDialogViews = new ArrayList<>();
    private Map<String, LoveLunchChooseGoodsMode.Standard> map;


    public void setMode(LoveLunchChooseGoodsMode mode) {
        this.mode = mode;
    }

    public PropertyDialog(@NonNull Context context, LoveLunchChooseGoodsMode mode) {
        super(context, R.style.Dialog_Fullscreen);
        this.mode = mode;
        this.context = context;
    }

    protected PropertyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_property);

        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.95);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 1);    //宽度设置为屏幕的0.5
        getWindow().setAttributes(p);     //设置生效

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        onWindowAttributesChanged(localLayoutParams);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ll_main_layout = (LinearLayout) findViewById(R.id.ll_main_layout);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_num = (TextView) findViewById(R.id.tv_num);
        iv_decrease = (ImageView) findViewById(R.id.iv_decrease);
        iv_increase = (ImageView) findViewById(R.id.iv_increase);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        iv_decrease.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        iv_increase.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        init();
    }

    public void init() {
        if (mode != null) {
//            map = mode.getMap() == null ? new HashMap<String, LoveLunchChooseGoodsMode.Standard>() : mode.getMap();
            map = new HashMap<>();
            if (mode.getMap() != null) {
                map.putAll(mode.getMap());
            }
            tv_goods_name.setText(mode.getName());
            tv_num.setText(String.valueOf(mode.getNum()
            ));
            tv_price.setText(String.valueOf((Double.valueOf(mode.getPrice()) + getSpcePrice(map)) * Integer.valueOf(mode.getNum())));
            ll_main_layout.removeAllViews();
            for (int i = 0; i < mode.getProperty().size(); i++) {
                LoveLunchChooseGoodsMode.Property property = mode.getProperty().get(i);
                View itemDialogView = LayoutInflater.from(getContext()).inflate(R.layout.item_property_dialog, ll_main_layout, false);
                TextView standrdName = (TextView) itemDialogView.findViewById(R.id.tv_standrd);
                standrdName.setText(property.getName());
                itemDialogViews.add(itemDialogView);
                XCFlowLayout xcFlowLayout = (XCFlowLayout) itemDialogView.findViewById(R.id.xcf_layout);
                xcFlowLayout.setTag(i);
                LoveLunchChooseGoodsMode.Standard mapStandra = null;
                if (map != null) {
                    mapStandra = map.get(String.valueOf(i));
                }
                xcFlowLayout.removeAllViews();
                for (int j = 0; j < property.getStandard().size(); j++) {
                    LoveLunchChooseGoodsMode.Standard standard = property.getStandard().get(j);
                    View itemXcfView = LayoutInflater.from(getContext()).inflate(R.layout.item_xcf_layout, ll_main_layout, false);
                    TextView specName = (TextView) itemXcfView.findViewById(R.id.tv_spec_name);
                    itemXcfView.setTag(j);
                    if (standard.equals(mapStandra)) {
                        itemXcfView.setSelected(true);
                    }
                    itemXcfView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            XCFlowLayout parent = (XCFlowLayout) v.getParent();
                            int childCount = parent.getChildCount();
                            for (int k = 0; k < childCount; k++) {
                                View childAt = parent.getChildAt(k);
                                if (childAt == v) {
                                    LoveLunchChooseGoodsMode.Standard standard1 = mode.getProperty().get((Integer) parent.getTag()).getStandard().get((Integer) v.getTag());
                                    if (childAt.isSelected()) {
                                        map.put(String.valueOf(parent.getTag()), null);
                                    } else {
                                        map.put(String.valueOf(parent.getTag()), standard1);
                                    }

                                    double price = (getSpcePrice(map) + Double.valueOf(mode.getPrice())) * Integer.valueOf(tv_num.getText().toString());
                                    tv_price.setText(String.valueOf(price));
                                    childAt.setSelected(!childAt.isSelected());
                                } else {
                                    childAt.setSelected(false);
                                }
                            }
                        }
                    });
                    specName.setText(standard.getStandard());
                    xcFlowLayout.addView(itemXcfView);
                }

                ll_main_layout.addView(itemDialogView);
            }
        }
    }

    private double getSpcePrice(Map<String, LoveLunchChooseGoodsMode.Standard> map) {
        if (map == null) return 0;
        Set<Map.Entry<String, LoveLunchChooseGoodsMode.Standard>> entries = map.entrySet();
        Iterator<Map.Entry<String, LoveLunchChooseGoodsMode.Standard>> iterator =
                entries.iterator();
        double sum = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, LoveLunchChooseGoodsMode.Standard> next = iterator.next();
            if (next == null) continue;
            sum += Double.valueOf(String.valueOf(next.getValue() == null ? 0 : ((LoveLunchChooseGoodsMode.Standard) next.getValue()).getPrice()));
        }
        return sum;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {
            dismiss();
        } else if (R.id.iv_decrease == id) {
            String text = tv_num.getText().toString();
            if (text.equals("1")) {
                return;
            }
            Integer integer = Integer.valueOf(text);
            tv_num.setText(String.valueOf(--integer));
            tv_price.setText(String.valueOf(integer * (Double.valueOf(mode.getPrice()) + getSpcePrice(map))));
        } else if (id == R.id.iv_increase) {
            String text = tv_num.getText().toString();
            Integer integer = Integer.valueOf(text);
            tv_num.setText(String.valueOf(++integer));
            tv_price.setText(String.valueOf(integer * (Double.valueOf(mode.getPrice()) + getSpcePrice(map))));
        } else if (id == R.id.tv_sure) {
            dismiss();
            mode.setMap(map);
            mode.setNum(Integer.valueOf(tv_num.getText().toString()));
            mode.setCheck(false);
            if (sureListener != null) {
                sureListener.onSureClickListener(mode);
            }
            tv_sure.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mode.setCheck(true);
                    if (sureListener != null) {
                        sureListener.onSureClickListener(mode);
                    }
                }
            }, 100);

        }
    }

    public interface SureListener {
        void onSureClickListener(LoveLunchChooseGoodsMode mode);
    }

    public SureListener sureListener;

    public void setSureListener(SureListener sureListener) {
        this.sureListener = sureListener;
    }
}
