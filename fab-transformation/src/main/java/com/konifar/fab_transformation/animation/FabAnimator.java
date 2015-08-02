package com.konifar.fab_transformation.animation;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.konifar.fab_transformation.ViewUtil;

public abstract class FabAnimator {

    static final long FAB_ANIMATION_DURATION = 150L;
    static final int REVEAL_ANIMATION_DURATION = 350;
    static final Interpolator REVEAL_INTERPOLATOR = new AccelerateInterpolator();
    static final Interpolator FAB_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    abstract void moveIn(View fab, View transformView, FabAnimationCallback callback);

    abstract void moveOut(View fab, View transformView, FabAnimationCallback callback);

    abstract void revealOn(View fab, View transformView, RevealCallback callback);

    abstract void revealOut(View fab, View transformView, RevealCallback callback);

    public void transformIn(final View fab, final View transformView) {
        moveIn(fab, transformView, new FabAnimationCallback() {
            @Override
            public void onAnimationStart() {
                //
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
                        //
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

    public void transformOut(final View fab, final View transformView) {
        revealOut(fab, transformView, new RevealCallback() {
            @Override
            public void onRevealStart() {
            }

            @Override
            public void onRevealEnd() {
                moveOut(fab, transformView, new FabAnimationCallback() {
                    @Override
                    public void onAnimationStart() {
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd() {
                        //
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

    int getCenterX(View fab, View transformView) {
        return ViewUtil.getRelativeLeft(fab) - ViewUtil.getRelativeLeft(transformView) + fab.getWidth() / 2;
    }

    int getCenterY(View fab, View transformView) {
        return ViewUtil.getRelativeTop(fab) - ViewUtil.getRelativeTop(transformView) + fab.getHeight() / 2;
    }

    int getTranslationX(View fab, View transformView) {
        int fabWidth = fab.getWidth();
        int fabLeft = ViewUtil.getRelativeLeft(fab);
        int fabRight = fabLeft + fabWidth;
        int fabCenterX = fabLeft + fabWidth / 2;
        int transformViewWidth = transformView.getWidth();
        int transformViewLeft = ViewUtil.getRelativeLeft(transformView);
        int transformViewRight = transformViewLeft + transformViewWidth;
        int transformViewCenterX = transformViewLeft + transformViewWidth / 2;

        if (fabCenterX > transformViewCenterX) {
            // Fab is on right of transform view
            int translationX = transformViewCenterX - fabCenterX;
            if (-translationX >= fabWidth / 2) {
                translationX = -fabWidth / 2;
            }
            if (-translationX < fabRight - transformViewRight) {
                translationX = -(fabRight - transformViewRight + fabWidth / 2);
            }
            return translationX;
        } else if (fabCenterX < transformViewCenterX) {
            // Fab is on left of transform view
            int translationX = transformViewCenterX - fabCenterX;
            if (translationX >= fabWidth / 2) {
                translationX = fabWidth / 2;
            }
            if (translationX > transformViewLeft - fabLeft) {
                translationX = transformViewLeft - fabLeft + fabWidth / 2;
            }
            return translationX;
        } else {
            // Fab is same position with transform view
            return 0;
        }
    }

    int getTranslationY(View fab, View transformView) {
        int fabHeight = fab.getHeight();
        int fabTop = ViewUtil.getRelativeTop(fab);
        int fabBottom = fabTop + fabHeight;
        int fabCenterY = fabTop + fabHeight / 2;
        int transformViewHeight = transformView.getHeight();
        int transformViewTop = ViewUtil.getRelativeTop(transformView);
        int transformViewBottom = transformViewTop + transformViewHeight;
        int transformViewCenterY = transformViewTop + transformViewHeight / 2;

        if (fabCenterY > transformViewCenterY) {
            // Fab is on below of transform view
            int translationY = transformViewCenterY - fabCenterY;
            if (-translationY >= fabHeight / 2) {
                translationY = -fabHeight / 2;
            }
            if (-translationY < fabBottom - transformViewBottom) {
                translationY = -(fabBottom - transformViewBottom + fabHeight / 2);
            }
            return translationY;
        } else if (fabCenterY < transformViewCenterY) {
            // Fab is on above of transform view
            int translationY = transformViewCenterY - fabCenterY;
            if (translationY >= fabHeight / 2) {
                translationY = fabHeight / 2;
            }
            if (translationY > transformViewTop - fabTop) {
                translationY = transformViewTop - fabTop + fabHeight / 2;
            }
            return translationY;
        } else {
            // Fab is same position with transform view
            return 0;
        }
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

}
