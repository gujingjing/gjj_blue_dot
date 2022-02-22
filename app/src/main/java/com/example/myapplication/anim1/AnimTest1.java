package com.example.myapplication.anim1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.myapplication.R;

public class AnimTest1 extends Activity {

    private ViewGroup rate_container;
    private ImageView image_like, image_dislike;
    private ViewWrapper wrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);
        rate_container = findViewById(R.id.rate_container);
        image_like = findViewById(R.id.image_like);
        image_dislike = findViewById(R.id.image_dislike);

        wrapper = new ViewWrapper(rate_container);

        image_like.setOnClickListener(v -> {
            startAnim();
        });
    }

    private void startAnim() {
        int padding = getResources().getDimensionPixelOffset(R.dimen.like_padding);
        int width = image_like.getMeasuredWidth();
        ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(image_like, "translationX", 0, padding, padding + width);
        objectAnimator02.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator02.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                image_like.setImageResource(R.drawable.rate_like_image);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                image_like.setImageResource(R.drawable.rate_like_image_selected);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator objectAnimator03 = ObjectAnimator.ofFloat(image_dislike, "alpha", 1.0f, 0.1f, 0f);
//        ObjectAnimator objectAnimator04 = ObjectAnimator.ofFloat(rate_container, "TranslationY", 0, -rate_container.getMeasuredHeight());
        ObjectAnimator objectAnimator04 = ObjectAnimator.ofInt(wrapper, "height", rate_container.getMeasuredHeight(), 0);
        objectAnimator04.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                rate_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                rate_container.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator02, objectAnimator03);
        animatorSet.setDuration(2000);

        AnimatorSet animatorSetAll = new AnimatorSet();
        animatorSetAll.playSequentially(animatorSet, objectAnimator04);
        animatorSetAll.setDuration(2000);

        animatorSetAll.start();
    }
}
