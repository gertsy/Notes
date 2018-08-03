package com.coop.projectnotes.projectnotes.useful;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.support.design.widget.FloatingActionButton;
import android.transition.ArcMotion;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.coop.projectnotes.projectnotes.R;


class FabToolbarAnimator
{
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    private int duration;
    private Context context;
    private int standardMargin;

    FabToolbarAnimator(Context context) {
        this.context = context;
        this.standardMargin = dpToPx(16) * 6;
    }

    private FloatingActionButton fabView;

    /*
     Анимация
     */

    void transformTo(final FloatingActionButton fab, final View transformView, int duration, final FabToolbar.OnFabMenuListener listener)
    {
        this.duration = duration;
        this.fabView = fab;

        transformView.setX(standardMargin);

        //Двигаем кнопку
        fabMoveIn(fab, transformView, new FabAnimationCallback() {
            @Override
            public void onAnimationStart() {
                if (listener != null) listener.onStartTransform();
            }

            @Override
            public void onAnimationEnd() {
                revealOn(fab, transformView, new RevealCallback() {
                    @Override
                    public void onRevealStart() {
                        fab.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onRevealEnd() {
                        if (listener != null) listener.onEndTransform();
                    }
                });
            }

            @Override
            public void onAnimationCancel() {
                //
            }

            @Override
            public void onAnimationRepeat() {
                //
            }
        });
    }


    private void fabMoveIn(final FloatingActionButton fab, final View transformView, final FabAnimationCallback callback) {
        //Прозрачность иконки у fab
        ValueAnimator alphaAnimation = ValueAnimator.ofInt(255, 0);
        alphaAnimation.setDuration((long) (duration * 0.8));
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fabView.setImageAlpha((int)valueAnimator.getAnimatedValue());
            }
        });

        //Рисуем путь по кривой
        float fabX = fab.getLeft();
        float fabY = fab.getTop();
        float targetX = fabX + getTranslationX(fab, transformView) + standardMargin;
        float targetY = fabY + getTranslationY(fab, transformView);
        ArcMotion arc = new ArcMotion();
        Path path = arc.getPath(fabX, fabY, targetX, targetY);
        //Движение по кривой
        Animator pathAnimation = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        pathAnimation.setDuration(duration);

        //Собрать все анимации
        AnimatorSet animations = new AnimatorSet();
        animations.playTogether(
                pathAnimation,
                alphaAnimation
        );
        animations.setInterpolator(ACCELERATE_INTERPOLATOR);
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                callback.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                callback.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                callback.onAnimationCancel();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                callback.onAnimationRepeat();
            }
        });
        animations.start();
    }

    private void revealOn(final FloatingActionButton fab, View transformView, final RevealCallback callback) {
        ViewGroup toolbar = (ViewGroup)transformView;
        View toolbarContent = toolbar.getChildAt(0);

        //Прозрачность
        toolbarContent.setAlpha(0);
        Animator alphaAnimation = ObjectAnimator.ofFloat(toolbarContent, View.ALPHA, 0f, 1f);
        //alphaAnimation.setInterpolator(ACCELERATE_INTERPOLATOR);
        alphaAnimation.setDuration((int)(duration * 0.2));
        alphaAnimation.setStartDelay((int)(duration * 0.7));

        //Растянуть
        Animator scaleXAnimation = ObjectAnimator.ofFloat(toolbarContent, View.SCALE_X, 0.9f, 1f);
        scaleXAnimation.setStartDelay((int)(duration * 0.7));
        scaleXAnimation.setDuration((int)(duration * 0.3));

        //Переместить
        Animator moveXAnimation = ObjectAnimator.ofFloat(transformView, View.X, standardMargin, 0);
        moveXAnimation.setDuration(duration);

        //CircularReveal
        float finalRadius = distance(getCenterX(transformView), getCenterY(transformView));
        int startRadius = fab.getWidth() / 2;
        Animator circularRevealAnimation = ViewAnimationUtils.createCircularReveal(transformView,
                getCenterX(transformView), getCenterY(transformView),
                (float)(startRadius), finalRadius);
        circularRevealAnimation.setDuration(duration);

        //Собрать анимации
        AnimatorSet animations = new AnimatorSet();
        animations.playTogether(
                circularRevealAnimation,
                moveXAnimation,
                scaleXAnimation,
                alphaAnimation
        );
        animations.setInterpolator(ACCELERATE_INTERPOLATOR);
        animations.setDuration(duration);
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                callback.onRevealStart();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                callback.onRevealEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        transformView.setVisibility(View.VISIBLE);
        animations.start();

    }

    private float distance(int x2, int y2)
    {
        return (float)Math.hypot(0 - x2, 0 - y2);
    }


    /*
     Обратная анимация
     */

    void transformOut(final FloatingActionButton fab, final View transformView, int duration, final FabToolbar.OnFabMenuListener listener)
    {
        this.duration = duration;
        this.fabView = fab;

        revealOff(fab, transformView, new RevealCallback() {
            @Override
            public void onRevealStart() {
                if (listener != null) listener.onStartTransform();
            }

            @Override
            public void onRevealEnd() {
                fabMoveOut(fab, transformView, new FabAnimationCallback() {
                    @Override
                    public void onAnimationStart() {
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd() {
                        if (listener != null) listener.onEndTransform();
                    }

                    @Override
                    public void onAnimationCancel() {
                        //
                    }

                    @Override
                    public void onAnimationRepeat() {
                        //
                    }
                });
            }
        });
    }

    private void revealOff(final FloatingActionButton fab, final View transformView, final RevealCallback callback) {

        ViewGroup toolbar = (ViewGroup)transformView;
        View toolbarContent = toolbar.getChildAt(0);

        //Прозрачность
        Animator alphaAnimation = ObjectAnimator.ofFloat(toolbarContent, View.ALPHA, 1f, 0f);
        alphaAnimation.setDuration((int)(duration * 0.3));

        //Растягивание
        Animator scaleXAnimation = ObjectAnimator.ofFloat(toolbarContent, View.SCALE_X, 1f, 0.9f);
        scaleXAnimation.setDuration((int)(duration * 0.3));

        //Переместить
        Animator moveXAnimation = ObjectAnimator.ofFloat(transformView, View.X, 0, standardMargin);
        moveXAnimation.setDuration(duration);

        //CircularReveal
        float finalRadius = distance(getCenterX(transformView), getCenterY(transformView)); //Конечный радиус
        int startRadius = fab.getWidth() / 2; //Начальный радиус
        Animator circularRevealAnimation = ViewAnimationUtils.createCircularReveal(
                transformView,
                getCenterX(transformView),
                getCenterY(transformView),
                finalRadius,
                startRadius);
        circularRevealAnimation.setDuration(duration);

        //Собрать анимации и запустить
        AnimatorSet animations = new AnimatorSet();
        animations.playTogether(
                circularRevealAnimation,
                scaleXAnimation,
                alphaAnimation,
                moveXAnimation
        );
        animations.setInterpolator(ACCELERATE_INTERPOLATOR);
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                callback.onRevealStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                transformView.setVisibility(View.INVISIBLE);
                callback.onRevealEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //
            }
        });
        animations.start();
    }

    private void fabMoveOut(final FloatingActionButton fab, final View transformView, final FabAnimationCallback callback) {
        //Рисуем путь по кривой
        float fabX = fab.getLeft();
        float fabY = fab.getTop();
        float targetX = fabX + getTranslationX(fab, transformView) + standardMargin;
        float targetY = fabY + getTranslationY(fab, transformView);
        ArcMotion arc = new ArcMotion();
        Path path = arc.getPath(targetX, targetY, fabX, fabY);
        //Создаем анимацию
        ObjectAnimator arcPathAnimation = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        //arcPathAnimation.setInterpolator(DECELERATE_INTERPOLATOR);
        arcPathAnimation.setDuration(duration);

        //Прозрачность иконки у fab
        ValueAnimator alphaAnimation = ValueAnimator.ofInt(0, 255);
        alphaAnimation.setDuration((long) (duration * 0.8));
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fabView.setImageAlpha((int)valueAnimator.getAnimatedValue());
            }
        });

        //Собрать анимации и запустить
        AnimatorSet animations = new AnimatorSet();
        animations.playTogether(
                arcPathAnimation,
                alphaAnimation
        );
        animations.setInterpolator(DECELERATE_INTERPOLATOR);
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                callback.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                callback.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                callback.onAnimationCancel();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                callback.onAnimationRepeat();
            }
        });
        animations.start();
    }


    private int getCenterX(View view) {
        return view.getWidth() / 2;
    }

    private int getCenterY(View view) {
        return view.getHeight() / 2;
    }

    interface RevealCallback {
        void onRevealStart();
        void onRevealEnd();
    }

    interface FabAnimationCallback {
        void onAnimationStart();
        void onAnimationEnd();
        void onAnimationCancel();
        void onAnimationRepeat();
    }

    private int getTranslationX(View fab, View transformView) {
        int fabX = getRelativeLeft(fab) + fab.getWidth() / 2;
        int transformViewX = getRelativeLeft(transformView) + transformView.getWidth() / 2;
        return transformViewX - fabX;
    }

    private int getTranslationY(View fab, View transformView) {
        int fabY = getRelativeTop(fab) + fab.getHeight() / 2;
        int transformViewY = getRelativeTop(transformView) + transformView.getHeight() / 2;
        return transformViewY - fabY;
    }

    private static int getRelativeLeft(View view) {
        if (view.getParent() == view.getRootView())
            return view.getLeft();
        else
            return view.getLeft() + getRelativeLeft((View) view.getParent());
    }

    private static int getRelativeTop(View view) {
        if (view.getParent() == view.getRootView())
            return view.getTop();
        else
            return view.getTop() + getRelativeTop((View) view.getParent());
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

}

