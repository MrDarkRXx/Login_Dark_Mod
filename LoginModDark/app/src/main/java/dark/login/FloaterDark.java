package dark.login;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FloaterDark extends Service {

    private Button close;

    private Button kill;

    private LinearLayout mButtonPanel;

    private RelativeLayout mCollapsed;

    private LinearLayout mExpandet;

    private View mFloatingView;

    private RelativeLayout mRootContainer;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams params;

    private LinearLayout patches;

    private FrameLayout rootFrame;

    private ImageView startimage;

    private LinearLayout view1;

    private LinearLayout view2;

    public String expire = "DTEXPIRE";

    private String Title() {
        return "DARK - MOD MENU";
    }

    private String Credits() {
        Prefs prefs = Prefs.with(getBaseContext());
        String lol = prefs.read(expire, "");
        return "Expiry time: " + lol.replaceAll(" 00:00:00", "");
    }

    private void addSeekBar(final String name, int paramInt1, final int min, int paramInt3, final SB listner) {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setPadding(10, 5, 0, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(layoutParams);
        if (paramInt1 % 2 == 0) {
            linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            linearLayout.setBackgroundColor(Color.parseColor("#cacaca"));
        }
        final TextView textView = new TextView(this);
        String stringBuilder = "<font face='monospace'><b>" +
                name +
                ": <font color='#fe0000'>" +
                min +
                "</b></font>";
        textView.setText(Html.fromHtml(stringBuilder));
        textView.setTextColor(-16777216);
        final SeekBar seek = new SeekBar(this);
        seek.setScaleY(2.0F);
        seek.setPadding(25, 10, 35, 10);
        seek.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        seek.setMax(paramInt3);
        seek.setProgress(min);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
                if (param1Int < min) {
                    seek.setProgress(min);
                    listner.OnWrite(min);
                    String stringBuilder1 = "<font face='monospace'><b>" +
                            name +
                            ": <font color='#fe0000'>" +
                            min +
                            "</b></font>";
                    textView.setText(Html.fromHtml(stringBuilder1));
                    return;
                }
                listner.OnWrite(param1Int);
                String stringBuilder = "<font face='monospace'><b>" +
                        name +
                        ": <font color='#fe0000'>" +
                        param1Int +
                        "</b></font>";
                textView.setText(Html.fromHtml(stringBuilder));
            }

            public void onStartTrackingTouch(SeekBar param1SeekBar) {
            }

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(seek);
        this.patches.addView(linearLayout);
    }

    private void addSwitch(String paramString, int paramInt, final SW listner) {
        Switch switc = new Switch(this);
        if (paramInt % 2 == 0) {
            switc.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            switc.setBackgroundColor(Color.parseColor("#cacaca"));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font face='monospace'><b>");
        stringBuilder.append(paramString);
        stringBuilder.append("</b></font>");
        switc.setText(Html.fromHtml(stringBuilder.toString()));
        switc.setTextColor(-16777216);
        switc.setPadding(10, 5, 0, 5);
        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
                listner.OnWrite(param1Boolean);
            }
        });
        this.patches.addView(switc);
    }


    private float dipToPixels() {
        return TypedValue.applyDimension(1, 6.5F, getResources().getDisplayMetrics());
    }

    private int dp(int paramInt) {
        return (int) TypedValue.applyDimension(1, paramInt, getResources().getDisplayMetrics());
    }

    private int dp2px(int paramInt) {
        float f = (getResources().getDisplayMetrics()).density;
        return (int) (paramInt * f + 0.5F);
    }

    private void initFloating() {
        this.rootFrame = new FrameLayout(getBaseContext());
        this.mRootContainer = new RelativeLayout(getBaseContext());
        this.mCollapsed = new RelativeLayout(getBaseContext());
        this.mExpandet = new LinearLayout(getBaseContext());
        this.view1 = new LinearLayout(getBaseContext());
        this.patches = new LinearLayout(getBaseContext());
        this.view2 = new LinearLayout(getBaseContext());
        this.mButtonPanel = new LinearLayout(getBaseContext());
        RelativeLayout relativeLayout2 = new RelativeLayout(this);
        relativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
        relativeLayout2.setPadding(10, 10, 10, 10);
        relativeLayout2.setVerticalGravity(16);
        this.kill = new Button(this);
        this.kill.setBackgroundColor(Color.parseColor("#bcccd4"));
        this.kill.setText("HIDE");
        this.kill.setTextColor(-16777216);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(11);
        this.close = new Button(this);
        this.close.setBackgroundColor(Color.parseColor("#bcccd4"));
        this.close.setText("CLOSE");
        this.close.setTextColor(-16777216);
        this.close.setLayoutParams(layoutParams3);
        relativeLayout2.addView(this.kill);
        relativeLayout2.addView(this.close);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        this.rootFrame.setLayoutParams(layoutParams2);
        this.mRootContainer.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        this.mCollapsed.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        this.mCollapsed.setVisibility(View.VISIBLE);
        this.startimage = new ImageView(getBaseContext());
        this.startimage.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        int i = (int) TypedValue.applyDimension(1, 60, getResources().getDisplayMetrics());
        (this.startimage.getLayoutParams()).height = i;
        (this.startimage.getLayoutParams()).width = i;
        this.startimage.requestLayout();
        this.startimage.setScaleType(ImageView.ScaleType.FIT_XY);
        byte[] arrayOfByte = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAMAAADDpiTIAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAK7UExURUdwTAMBAQEBAQUDAwMCAwcEBAMBAuWSkuORkf2hogMCAwMBAQcDAwsGBgMCAgMCAwQCAgMCAgICAwEAAAUDBAEAAAIBAQUDAxwOC7mbdBoXGSgiI3ZbYCwqLXpiRyUhJDs4QY9wT4VNS1BOUpVYWU0yI004PWZIOmdeZWs+HoR8fP///7QUFLanyHUGBgAAAP/jq7alx7gUFP/hqrCfw//fqf/Qnf/ZpP/+/v/VoP+1ebSkxrETE++UlLsVFbKixa2cwf/cp30HB//Ek8Cx0r2u0P/MmoIICP+5fMOz1ca12buszG8FBXoGBsEWFranxwkJDP+9j7eoybipy/+vg//Ckv/AwPj2+fz7/Mq53f/Akf+/gP+0h/Px9joBAf/nsMvC2P/GlnIFBSwBAf/Jl9jQ48gYGOjm6/+rf4oKCv+xdAwBAN/Y56sSEsW605MMDP+3itLJ3loDAyACAeXf7KMQEBgRJe3q8lECAhYCAUYCAv+6jQ8NF//HhiYkLpsODjc0O72yzCAeIy4sND46SlFPVGIEBPe0YXl6elVPYhgXG6GUskpCVeHg4GBXa8LBwmhgde2VOGtsbOuNLNbW1vWuWGkFBfGeRV5fX7W2t6eauczMy5qLq+mFIUpHSe7v7v/vtYt/mnBnfoaGh/6mcJKFopGRkjwmJ4N5k0A+P/OnT/2goHZuhv/Oj6usrP+QXv+3cP+caJubm1I7Mn50jCoZF5NxeE0qFGAzFotjbaOipP/4vCwTBDMjSTseDdC/42hCLGZPSdyQXMqCUrd0SctgAqZmPuqbZt50CJNbNP/u3P/37f+xsX5NLntBEKN+gtiwp/7lxP/Myv2EVf/c286pfeLBkZRKFtd/J61VBpZ9XJaTofunPaiKZqtkZLxuJeqvf+CJidB+fui9tMihnLqMjMB+Zrg5E950RnoVAgAAAAArdFJOUwAuga/9DfEBBQ3NcloYkb4j5tk5oGZFTv7+6sT+8P7X5v796kDfre7h6dmwmHo0AACwrUlEQVR42uy9+VNT6bovroBgO4s4de+e9j77nvu9qVRYRYBgqiuhXItiJT+AiW0SY27S9TVFF1CUlFRTXoGKGUoCGCJiydTMowK2ghs2ehDVdrbPUVu72577nN23/4z7vO8aslayAuDR3XbCS7cKShLyfN5n/DzPs2LF8lk+y+clnFWrUpbfhHg+m1auXn4T4vkkJK5afhPi+Kxcs27ZBMSzB7AxcVn+8ewApK3ZuvwuxO9Zv7HDv235bYjf+79mgH530/L7ELf+X2qPc2DZBYjbs+3Ngau6QMLyGxGnZ93b9Tq6wLXsAsTpWTvSflVNdqSuX34r4jL839DZVakl6N605fciLt3/jS02mV6pN44sZwHi0v1L7VPJSrVqqn7zyuV3I+7O6rXJMzKZuUxZQI9uXA4C40/9p3kaZTJZhYYg6M7lIDDuzvbUlhMgfzOpIcj65OUgMO68f8WgGeSvKgUFoAuuWaYCxJn3twarf5nMZtQQlL5mw/JbEk9n/YbCpiIsf1OZVk7oBhTbl9+U+Dkp61JrxmXMKdeq5UaLfzkNGEfi35KY1HSClX+lWiknaMK9bAHi5qxMSxpplXHHoZXL5ZbhZQsQN75fWrIbO//MKVUS8gKKbli2AHFxVm9PTKrpK+LFLyuSa0AB6OoLly1APHj+azcm1QwKxC+z6ZUGudyoCyZvWX57Yv3yb9mwWdE5UyRTheRvKlPC/ZdTas9yHSDGz6atG5PdTY0mmfCoHEo1AgDdo1iuBMfyWbVtw+aqzotFsrDj1GL5G0jfsgsYy1Hf2o2KkcFmWcRBGQA4BNVWtVwIjNnLvy5xs6e20SxTqcLlryrTGhAA1HRw83I/wB/No1u/fuWW7evWbYWzdu3ahLXowJ/Xrdu+bcumTetXMV3emxLW1DW1FsmkjgoyAAasAJTLWcA/juBXbdq2LmFD4sbUzZuTk5Or6sSnqrAQvrp5c+qajYlpGxLSUusGpaWPMgAFGqwACF3v/FywVeuWqWKvhTJfv2VrQuKazVVuV2dL7eDF8dbG9ubmE80nQqe5ub2xtXV85uJosNe1WVFY09QeTfwy2zHsAMjV5PyF4NXb0tYsA+B3F/7KdQmJqXUeX+1ga/sJJpZTqVizrhJ8yhr6UsvA22/WNptk0Y8DUsDo/huujs7jAaRsSwv4pD3E1SvXLTNI/hknZeW6DRs3e1r6WpttskUd89UeV11T87z/ppyJAMED1NRFzQGs2p7Y2UG/t1IqsEzYmKxYjh1e/dXfnrBx80jTTHsXvuOLkr/K0OBeQPxI/tgBkOstgY1RxgJtSdjYUk9ejWwXWbV9w5okd8NwQ/KybXilZ/26tFRX00xzl2wpRzXzia9dNj9WnFqCkb+crt+8ZYX0DX+7r92ps3SEGYhV29NSkz2BHi3pVFYtRw+vTvGD9N/s7GtcmvCRc1/bOW5a4N+UKjVGRv4U1bk20uHYmrbmzZbWLpXTSFEuoZ5ftSVhTbIr2KGmaVIPymNZBbyqu799Q2rnYLNZtuTT6qstWuD6y0rVGkb88gLLaPLaTatE3uaGjcmepnHgC9lIrd4SXLNemFJMdvXW60mSMoICUevalovIr+TANescbJa9wDFf9LXKFnIVKghO/mq6zRUMvJ24dcsqXvh1oHaws1mqURqv9vBEoVXb0jZ7gvUUTbHKQ04AkXBZBbwCw5/o6muXLdLhC5N/a23Rgi5ChVrJ2n81ZfAGr+qotuG3Nmxdt2FNcpKrabyZ0R8mh1ZjsNRzVh6YBG7/AGWhjQQhl/P4WeaRCM+mtWvXbVm5/r8VHK/csMY/3iV7wdPVuLDRcCqV3A3W0w0NOjia4XeTFe6Wi60Qa7C4qyQhTKDb3EyzyKYNqa5RrcVCydVy0dH5llVA6GxIqitMhixsYloCIGHT+qVTLLYlvj3qML+o+GUq08I6olzLyt9gJC1Br7a+t8FdWOjCRj/0r5wGLVFAtnlwjnjlhlT/gF5H6wWXn1UBZEfSsgrgdfdmv3KgZ7I34PO63HWAhI1pCVu3g0ZYJBBWrdvoHS5/Md2/2GNzaLD9N+gpyqjp3fy2113TWTseFmhWlimVBEGrXcnbkfjfDNSTYPmJAnn4UWuM3uVCIne2Jg1YKBIOpZdr6zt6RoP+EU9dMsIB6IOFYLB+60ZfT7lZ9kpPEamB/A9IX69sm2yoqRppGmwsUoUFDej6qwvUOsqbtB3KiW8G20hSb4iUvtygVJcDnXztsuiZk1ijMeK3yWAwwgUjaRqujRaAEGjwuBkYRLUK67e+G6wnK16t+M1OvUatUSqVbR3Do8O1fRfbmRpCGEhoJYBETesakretXvtmUKmjDHLJo9SWymyUJ3W5IsC4gJsDJP9OFRDoyI16ktZZLDpKWd/T6/fWrEncsHVLJPlq1dp3g1oLWfpq5W8r02qVGgNZVjA+3tpeFNVGIB+BIGn/5nXbNwbaLGQBEUX+BAKsbVSxbln46KxVDJDid4jA102p1GiMFOBAR2oABr631yQmbBNdmq1v9Wp1OqXj1V7/Cr22gHSUVppMXSZZlFSRCbQ/JglQpH/zhjR/vU5nUMujyF/PYMjhTlxeMcBaAEokew2hJx3cKTsGhoGiaR1J1PcEG95N28r5TtveCmrBw1bKba9U/qUk6aw0zetjmJzHtJgkRFB6f3Lqex20Th/l9oP8yUr220aTllsKsAXw03rOOQYnmnQ4i2xms4o5ZrOpstTpOKbWEBRJk0btt73vJSIMrIRrBp8XENryV6sAKitNC6kIUomsv7xATVO+JPewHn6eAmnpGwgtzeO1uW45EoSzTtGjwy6AQaNV6h0V4W83JmvYKsqPKbVq8BFJWj0QfG/D1nfrLTR42Aal0Sb7XU9lmUapRo6LWm0hvQq/ltazjozEIbRk6OWqapc55XDS6rQ0SF+u1mocpfMI01RKarXQjkkYSZ2+x+sNdsDvajVR+ruK31yu1Go0TBBrMbrqYG4w8wmlNxoNhvnkL5O1Jy13laxYn9pAUwYC3sayooXNMe7HIQhKp6sPNvgHSFpP/p4KQFVBtoH117fV19e36bVvrmm7SqghVWhEqgrlii16kSuoJcX6zbc8ZHzFNsWkRa7UgKNlXkxArsZvKEHodbq2UV+go2fhIs4rjA/K9USZo7yoaLCpxecbqUte46mrgoNoxu4aV4PfH6inBPLXlAnk33xxXNZYuOwGJhTW65RlRaZFvulcRR5bAuOop6Xd9LsBwFRUWWkzobiwCxGLx8fbWy9e7KttAjSMuOB4UpMVAzph/CfQVhcn5iZa/ldq3LuBqzZ6KH3Z4oVYqeFK8gRFdwQHi35PAyD5FRVDNDYVlXek1vXojXwAoNYI3JWZoR8++OCHNWvifsIcpAEtmsVncs1lbEmuwKAzBlsaZa/rsZVeHXB722gjHw+qhfFq89AP5z744NwPyXE/YxIFgccWrwAqGE4+cGp09b7artdT+JC5KKN0o1UBiiSkHUBzy0OQPyDgz4p4twEb6rTU0hWAWk8Pd87IVK+h9M1dlQ6l1kgF3MMWShACKA0CB6B16GcGAB9cinMbsHqj17iEVH6ppsBoIOQGmgi0tL+e1181XttmJLU+b9tVo0D+GrUA5jbfn7H8kRFI2h7vLoCucvG6lVRCxggIWR2dr6v6h4igtba3J9ir18kJIQFEmK/iFAAA4Ocd8W0DticNLyGT50QeAEGSoyMzZtnre2ytLe916GiDAABKUcGiiVMAyAuIbxuQUKhdvAcIg7lQwYXw+9plr/NRqboGvYgQoGZDAIP2mDBf2TzxCw+Aff8R3+TQRI9zCQpAC3lWS5u3qUv22p/mWldQbzESLANMZOYG50IK4PG+oXiuB6xKDZhRUnTwYvvCOt0ECsBoGXD1qV5/+QNdrNHn6tHhOEBjEKWrbJ0/8AB48Pjx3+O5HrAleVBm6nPN/fnahG/Bjh6YzEWRva5x2R/k2GY6G9qAGkBoxVqudeID/uzb9+U/4rkmvC6p3dwy9Qu8Ez//+ufO2uYFFACtbOhsl/1xThHYAQ2t14oD3dqQC/hg3757D3bEcTIwwSUbnPoAvR/nzp37+c8TfSeiv5vlSl29q6VI9oc6jT5vD0kqhZFOV+evQgB8+eVEHNPD0/7S6uI94nPnfplzzUStAuktA3VNpj+W/CFXedHVoNTpBU7g+MTPAguw78v7f0+LWydg9ZoNCSF9iCDww1BLFDvgtEwqamV/wHOixT1psdgkkwAAgMf3/xG/mYBNm9em/SIAAMqMzQ1JKgGzJZD0reyPeS66G7R8IrhoRGQBAAD/+WbcZgK2bE74q0j+jBJokjD0zgZ3o+yPepo7P+kxhSzAOYEF2Pf43uOheC0HwP6lDX8OAwAg4Oc5wVoW3nNyNcv+uMc2+UmgSNIC7Hv85ZfX4tYLXLv5rR/CAcAogUFzmPw7T8j+0KfVw6wa7BJmgRAA9n157+9xWw9KmPhA8pz7ZahFmO49UePrkv3BT2Vt3SCKCwUWgAXA/X/EbS4wbe7cOWkEfDA9ElL5zVUtZtkf/7S6fUWy2lAdALsAyAn4j7gNAxL/LC1/ZAb+XMM5Ao1VTbKYOCc6Xa3/LrB5rAb48sGbcToqYvXGv0cDAHYELjIXp+73Df9fovIx99X9WaDzzjEa4Mt9b8dnd0DKqjU/RAUAvD2/DtVC+mS8ru939uBfZvZxvGbq5zAEPP7y8UScVgM2pWJu9IMHD6K4glN9svHC31n+siLny2w+ax45/IvYCKA4ME4nRazcjJJi+x7Du/CAeVN+FiPg51/+V9Lg7x7DHzv2Ml1QU1PJD2I/8Msv/742XgEAl+Ec6wvvA0XwIMwiPPgh+eLv7rqZHR0vNwfZpxA4AhgAf4vP6fEpGACMK8ydB2L5K35/+UMWum3g5WYhxgtD4S/68b/89zjNBG3Z/PO5c/vER6AEzv3599f/GABkz0tmITXWzf4sQMDjf8QpAFZe+vncg33hCODl/4Oi73WQv6yUaht9yTyUEx7eFTyHAJAWpwB4cx4APHhd5C8rMtC9My/7MUdK2Krwzw/2Pf4gMU5NwJu/RMh/H3sxHvzy2tA/Kg1kR8vLrkWYfIpfWazve/wfifGqAX6JkP8+LgegaHltarl6Ne1/6Vxkc4viBw4BD96Kzyhg0+afH0srAJC/77VJ4ZuOKXWTTS+/GQEQcI4xAufiEwCQCYwAwAM2A3R45PWhf0JPur7NNy8fQWV6kVxRE4uAD36OUwCsXxMBAK5rvuY14n+goQRUYP6MhNmmenEEnPslTgGwauM/WAA8ECqAc+emCl+n9g+VAwAwvIBLYnohL5FBwLlf4tQJTEn8x5dhHiAqBjyYU7xe/E+nUg42YH5MqoqKXtgPOPdLnOYBVqT9+5ePIzwAmJszI3u9AKAljPrAAllJc/uLsFbNPsUv5+IXABv+du9xeAgICcDXrf+jVEnIyeGWBdxS28UXQYBpJOmXB3ELgLV/EgHgwesWALKnQqOWU/ULtqU2174IArpqjvz8c7zSgtdd+/JLcQhw7oMr7tet/1NVCdNJ9Xr/QpUp1cwLtS42V83+EK+rQ7Z1igCAFMCE4vXr/66Ua+QGXe+C1GRT0wtVL9oV8UoLT9n07uMvRRbg3N9fCwZAuHU3aOQE3TGyoIY/MfIi4YtqPDlep8SsXvOf94QW4HWqAIQDgKK8C2NzvPOF0gF9SfE6IyLxv0Je4ANwAI7U2F4/+atsRhhQbbAEmhbO97a8UARj9sXrEsmEfw8B4Nzr6QAgDWCEpdGErmcR/YkvZgRkRZ6N8dkctPVPIS8QWkEUg7LXEgB6BABS6VqEcC/6XiASUKkaFfHJC93yrzwAHpz7uWTE/PoCQG7UNSwCnybfC4FY1Refc+NXQRjwmFMAE4rXdAQAEALQhHpLsGURt7vR80KFTNNIfE6LS/wP1gl48BqmgHnh0BgA9MDIYmTb9GKBTHNhXKYDwQv8ko0BLrlf1xkA5jIt3gqqcbUuQpmfqGl9oWcZjMvB8ds5L/A1aQKJBgA88pf0LirT1+fqejEjEI9jAjb9lc0FfnDY86pJYKoXpfWpGAAQusCitHuX68X47HEZCaS8xeYC/6xofdUX+cWIe5gSxACAHh5Z1N0ed78Yoa0pDifHp6zFnJDHH5S4Xv0McFvFiykZMwsAqt6zuERV54uNNDlRFYdVoXUTyAl4/E9QAGjvaLnpv6EB5EaNZ3F+SqP7xTKag/FXE1j51gQ4AY8flLj+KXVdymH7bwDAoPcu8mq3vFgo2FUTbxnh1W8N+P7r3r7HPyj+OVsAyjVlthc2AXI5GehcZIn/BceaXlTEVyiYsnXUEvg7pIIu/ZOqgJV6LVm5dA1QxgKAGq1ZFF1JpWrxvaAKSFwdTwBY/57G0nMNlmb8s6pAQPHXGJxLdTdN3L5acqBukTe7serFVECcMQMShmlSe+2DLyeq/lk8wErY4Kd1LhUAx1gAUPWf9Czye3ydL/T6iurS4koByEkjHfzHA8U/bxIkmHONsnRp32PTsxvL9RqPf5G2qj3pxVRAbTzlAraO6ozAs/jTvyb98zqBgOFrUBJLUjgqm5wFgFHv9V41Lc4L6Bx5odfXnhQ/E8NWv9VGE4Se+svmTrFVfpUpIezQifc4LqyXNewSYCPl9xidi3utrS/GbjKPbIyfHICfRrr1ajCsDGR6pbSQCqVGXqB1LCEjpOJ21svBXrmVojBCZYv2YlWuFwoEVIPxYwMShi0avVGv8yaJUuemilcKADOtJeRqZfkSrAD0BrIrYOnRujayTPj6KqM+zkzSC/FbmuPGBqx6S0npnUZdWxgV3Ol8pU6AqhT0uUFjbFy8eMoRHQAfuqdwwCKKIkyl0ayJyR3dtVXNU7EaiZde8S1+WllaJLcExWWAIv0rDglNJLrPGv23i0WA2aHkAEDWV41aNAahEShyRlNYfXVRfxKzKToE+uKFG5YwTBWYSo1kjdsmctKOvWpeQLkGLaHXd9Qu0hOECSE8ANrqgha5VmgETOUVUe7yiaqopSNVZfS6cmuc5IJS3qpXl8scZL2YClhBlL/ySNCoxiFd08zSAaCs81uMapERKI1aXvBFjwRtjqiUgRN18eEEbHpPY7DJyuigaByIqkz5ygEgK9NgglfHIvfQmkIAoOQ1Xp1erpTbhH/tiEYMiZ7fMJXPREOAqTM+koHrgkYH2GOj2AJUqOtfPTGgFOd1jLpg31I1AEW5XLRebtCWCWx4KREls9g1jxtYqumLZgVq18SFE7ChhyqVFZEdIgsA/tbAS+SGqmzHuiRz+wgBal1952L8QJUAAHra6yEpwqDWlgozxfooRqCpLpqQVUW6yWjwa42LTAC4ALQN5jCLLYDNSAy/THKwSfutlAicygJkBCz+viVqAD3d4NZDFGFQ6gWuarnSIR0JtEenOZgc2s4oBqK5cHtcuADI2DvlHqEFgJybofdlAkBV2dMncTtRvzeoALp+pHlpPoBR56vS0CiMFLoqlUalUzqmr4meDXSSvdJthKqumnjwArf79RA+ldcXtoSlXAIv1QcwO4KD0jVBpAJ0i1IBZiEA/IVaDAC1IBkAjCGDtD9ZmxTVz6yAZtMoYYgvHrzAhFGdSWa+OikaCQeFd03Ly+0Qr9D6JBBVpESRIEF2LKbZS6QBAoVtGABypUMlcAO1pORtbow+8c5E6kY7pVVAbWIcMAMTB0Br2iwNVSdEhRpjfcvLTQSaHT2+IolLzRC9SV/fEjVAIKmeAYBawCqwkRrp6NVUF90GlBspl7S5uxgHucBV72ptyH/+pFMsFqqj5SWzAyuoQJNEQYBVAT0jRUvUAEn1ugL8Z6XA9y/XaDSSD9SkiG4DNJZJ6UaTeAgDVr5Xhm5Oh2grCBAvyOGXPZddVd4WuY0etfxjgRLewaVpgCAPAELrEDLNlGVmSVZA9DjgmJF0ST57c1XMJ4NTtvuRArX1ilgTcC3p0ZfeI16hm+yMvIYOpsZPRrHDUTVACABALSsSYESukUoHFRU2RUemVietAorcsT81bG0v0p+VXlEa0KEsoIMvnR9sdug7Iy19hRqzfKg2z/hSfQAOAHKB51cKVEPJdNCI2xadmmI0SHoBJlfsx4FpA/htq2oRJ+iMlO/ld4hU0vWeRulsIHB8/L4F5z/SQgAoQgAQJANgjpRc0gj0RR17AlRDJd0ruRijZUOs9wimbDwWGSVVqAmjseHl80NVDl0gMufC2IACumPBJh4TqZELANCmY/lBQCshKvnn0IIb4FxaIFim1Ws9Un9dG/PtIevfdWL+m/B6QNMGQWk7X/6gOJWN0nsGI6sxGAB62rUQJ92kFwGgwyLnTygZAFRDg9Jgi8z514keXjSjoFwrp4MSKkA1GPOjIla+h98qn7AjDNj3kJnpfAW7YlSlWk9Ey36lnqH6WiYX6ua3GdQCACR11tNG7nM1TzA3kWAntI7IEGbEIzQMZrPICVCT9W6JPNV4zCcCtrUwrLkWsVtG0AO+VzEoyNZ5ay7injs0TC7I6F7A7yxSczpfrtc1XLlxrQfaGSJUQDkwRzXKSHZQU6EQXyahjqiEmhLdIJEqatwc64mArdgtbxa1BDqVBKEbbnkVAOh7aL1+KdzUlzP9XlATXCASrNCEAEB7r1ivTwxbjKxS0KgreJgAoJSRfLZBUblTRHk2k1qDTqrdsLku1hMBCdjXHxdlAcoQAHpbXkGbcOt0cbX1C/EUSpWZZiw7oeuom9/vcCp5m09Rntn8setDw1dZBBTwrr+ZVIYVCTmKnxDlJqdNlAkgKNrTJFEPjPUFAmkY9bVCvoQJvYGWYO3Lbwponrg9tst64cjF8OieteukZ/7kk0MAAMMn01Zr9fVLk1dZK6Dmtb4TZZfVRHj3+YlC4aOrnKXizFeBlA+iGonxRMCqROz9j3SKqJqaxdZnl3a6rt0a27Urv/puGEu7lJUroQvWzGd3VGV8ECAnNVXT1fmgA65AW6M4G6SqRL6iQRnuB9rcIiPvFHJHYBNFAamvixgnomqJ8aGR699Csuiq6xNZWg3iW7z8QQG1D635u3btshYfFuvaSoK5wwRVXzdf9sl0TB0CQFvhw+r8XfljNy6N0owO4NlhZgwUtTo87ewShQEVwqYCbDauBiK7CGtjnBGw6T10a9oLW0WWVi3Xk50vfVrkzHS/FeSfv2vsprhXy8bGgXID5ZpvpE9lKAqU0x2KuwAA0CeAACYWMGg5x68cFxiV4RzhFlEyuEjkJUDqg6Drk1oiEgExPi5s5V9wsFPYKCLpEAWUfuRlZ4Kbh24g+SMVYJ0VKWMzqWEyvAQ9rw0oDUWBBnpYcbMaPVh+9c0dwxYDziVxXQIVBvR4GnmYF9Akmn5RqRGyCQH1BUZ6JGJR6kyMZ4K2Y2XcXtcqIgNBTK52vWRSuMmFbyw+1TdEERl4gUaMgAKqo2oe3JVrBAAYVdyoZh/tZuHAVTmB+wyZK26jECCIsAEkqr5CoeKplGsrxCUptW4yOVwFNKbG9gaRddj4n3DPCLMtaDFTm+cFM8HRSARNU1ZWAaBbO+UyiTKxDADkRmPNPDZA4AMaSb/idjX3aHfZrDDn+JkdBOMVigOZiyJFBwBwiD3fAkqbvDksF9D8ZmxngtZiV6+oRuDxlWrVKCSXBMB8rZTMP4i2u3vm8IWxXfypvi6qzJRqC7iLHYhK4OfKhuyACO+VYg5Q1uqHhfU6A3b8mEvtVCMVEM4NGhe5OpVyJbiBquYiQZ2JbEgNA+CJN7fENAA2YDnYXLXC60gAAAY8UsVTU+UCyQFbhbRWaHffHMMGIAvDIN86LSw+8HMfoOs7OnEHXVI+EUjUzHIaJX9XtXW6qp7GCWEmG1RBGDBTSJwMahUDgMD0wYvjnHpBy2gmU9eIVUCXK6YzQavTmLfE1yImasO8IEkA2ErnB4CqQro3y9T5BXYAisfs6dXF6NJeFw4jqVRz7j2ljE7cKdWEggCqrXCOdylAo+yaqlGiZnM1wway6ZmwQGwDIgCggQE1rYMm1sHAw8feTBSrAHNnTHOCViUyil4wTRHvZCAswy6pylzlAiN+TY5SSQVQO4v1dXF1uj0901qMVUBd6KFs/N2GmrDbFDURTITSAAMK3qcshsez9l/5N5IC28X0CprLcFyhUVfOAwC1hgC4NNcWsfBiZk+lbRTbvpaYTgWu2sjc89pQep5ZzGWZlARAUdkC1Rp9hZT8G0uwx16cn2tPT7dn5zNeQJ8w+Wzg+z2ijnQqCyWCgUHIBwEHx6wAgerbJQ0WyCewZcBy5gHFcYAIAKpKyHcpy2Q2ZtG0qhLFEUa6N3GtSAWommJ6dcD6dxkxD4YGr6L9zACAXpcUi7pi/gG/5jK11PjXLvdD5rbmgPwBAXlIBVRPJxWJtA4LgN5os0r5IZEYAP4jt1kXILs7MwfMCoSWQQskhJiZEUzd0CDmhok1ANpEjtzE2laBi0nXb94uVgG1MQ2ATe8yPneru1nAjcDLOSW3rTjnn+pWWWCQAoCfddjHsPwBASCv/Orbgm7kci3n3dOTiijUwEqDIAgw1szuwmnF4vxsu92ePoaDQSgOMwxhFWtU1EJmkKpVNDW2FA2cgkhw8CJHg0JOgN61dW2LSpgKjOlc8Mr3mDe7PTRW36mVRwdAuX5eADglx/7NKK5Xsw5ABgOADCtCwHRhUegb+elfPYpLRVGou7wPSCAfENcVdhVbM5BZsefkW63TQBOVM9E9O1SYEI0inSmMAIDSoBqvNYVqzXpdS9qmVKEKuBjTk6K2MM4f7NcaDwOAX4onD7xu27ykXVSQC08ENOOqDUgqk1UA2A0oRl5ArTD3wAHgSok0N7Rcy/uABPiAt5hEcHG1nX3IamvxlU80ZIEG33pGpxhE+f7BQr0IrbiAVNne1MXaPpRMtPSlrtogCIpV4zENgO0tXJ52kJcxughGi9cl4e6ZScN8I96LCK0EHxuYO1bkAGbz8gdxZe0SqQB+AigkAqZK+qRrwVreAkDduOQ64wIUZ3F2JX0MIOUl9QVY6KVMyKARBoJ9hWpzGAAM2vITLSdCTgChqy/csrJO4ACPx/S80HWcx9tZK+q9oMo8UgAAtuh8AChXgtNliiBiMSnbHIH84YwVCwMByMqxGkD3t+nZTikngG0h45gjV4oZC8DjKgPMQPUtRa+O6QvBviwItKBSWAzS2ASEEAwAJVXJdkFjXgpBGmAmwEYBBFs3xnI5cCuXdWlqMQsAQNAFhVJ9EkVqZcV8rH2lxlEUFj02J93FDkCeWP72jOrifOsUV57jpWvQ/X36oSQ5WOgCkMgFYBSANT30wPbc6mlFhw53BdjYmEEjCARbaoyVsjDHU62pbBrnvoCgr+tMXLFVsDmzcWMsdwas5S5+X2cX1yOBNIDu22QJfqaqVKONPjlMVUmo1Y7m9rAa4BXsrY+J5Y+EBeb7BhfyASkzBIBbRxolXYCQBbBMKm6yABA9sN2+6/AncloJngjHMxOSAjq9VIWQBsjwiCpqBwU2wWgJpq5avzmUpmyP6eVBCZzpHx/h76IGReOTqVJTU8o1Wod5Psqm2tHaHNaPhfI1xbvTI449B+R3pcbEJQJ4EzB748qgVIqBnxILQmoo6WezAGLLYu++ofBb9CgfXM46ASF2sM3TIAQAO5tE6+yr5ZhpBO5OgJbwtFBirDmmCQEb2Lda1e5qFvRe6Mm/bOy0SXR2aaRbrxkRQeMe4RgUqe92BWKBFVdn2CMRAG6A9RZb+uG2gSEAXLk+K1EShsDewFsAQ9VUPpMHtoofMqPb/hCyARrIBzsZk6GW80HlCXeQKo1AlNIx2MQxRNB3WAYU61Zs4/kKqhPvxjIA0jhVV8TxP6C/Hgyh+r00CQBAhl1Jmubr2jAeE019NXlKIAVUbM2Vkj9kA/L7j3jFCh4AcPj2nMss4QKEgkBLDwSBDADywh/5cs6OKjUJdMAKggFAyAlorBoWrBjgWs3V9EVW2eGBVYSurWrtilVr+JJUjAOAr8pzFMBK9L6R9RsTIvMAKhOp1kRNBKgg/U5QM6KpEr1gACDiz5aSP7jtufnWu2zmnw3yDQCAI7fvVp2QcAE4BVCgtzQo+Dxw2ENnpF9+qmigwfKz22UKQk7ATF2Hpjyi/gCvmfV/8FcIWuMGInAC30UU0wBISeTzP021Kj4dAtmYtK0SmUBg5UYHALzhBjXdJ4zhCxTT1daIAECYDbD2s0uKnEwmyGBEALgROdhVMBmggNYUTrNcAGt6RgSsLl9T9FB6E9MeIpwfU1ujFOSFoLTA0ND0beyIOmyHCGhfhclQK5M5P6To3RjuDlyV2MqnvFtMIUVOBxO2e4okAWC0Ra3WE3I13TQuCLRrSvqrd3GpOskzBnFbEduYwQGg5PbtknBGsspmCNEBdYgPyliArMjHzrz87EgNqvOyk0c0Gs7x83n1AhJYaP9UvatRkGuEnjNw+1ISXcwPqoptAGzkr1orqwdRGc2ob0hY65YAAJgAg22erh1Dga89FDP2Km6N5UMGWHTp7cI7a88Yu85EgqgXgQHAaMnt4pLaSHjJBV2BR4qZUrD4sXk34LlilCrj6APMQPFmGEjuCpBCABh5AHhaBakGKEijCcFbOTcwxgHAy6uZndaLLjIMSxhvLTwhAQCNpiAKAFDrPkF18C3FZmdb4Wy+NSwD3A1HAAEIDqyzuFujAifuCKMRysHXrYd98zSFkW0KlgxUPGaX9i0ufULobWwnKeKFmS66FXUXPcOkeJoUCwB+Nk0loUQxZmAzEIE3ba6NAwCsX8NH7SZ2iiNq06KgSbOxsFkqCtBoogAA0aoJerKJSyg6aD94gMWhON2OTtav7z+7fNmeyUPAnn4Lu4GVcgYAel0QUsezruh8UAOCSLW0C8jFgjsVvbSTaSMh5OTVRp8CTlINIQIAxzE3armZFZhxAIVQPBsuzdPFAiB2ncAUAQBkTYN8UZQcrjvRKDFhH/IARJt0rVaFFC4Raih0UHBPrYJSjT0jN8d6Y0qhOPLO0+7Ldh4Bl61HmvhiP2HQw+CP22PT7q5weIX6wo0cHRTqy1GcS/tQldpSRKoRpCwav4I5hcN0CACCBWQaVx+HWhSMWoLJW9D0tKTWmAfAik1vhvQ8mw5xgm9MBt3m9qRxER2crfYY65uj9G1CN4mebycq1ThGIFlXfJCt1dpzgQ18c4oVxLWn3d0hqzCHpM0WAxAASm6PPQxXP+W8BSjgK8ER9SWBF/BU0Wspp0EnUeSwW8Gdwg5jeWSruVHOc6JxLGLpRQBYsSqV+WrRWzGsAYQAaMXpEBUGQEMNFHEuirxwMyMHqr4xShAIAKDrXe2YDmAzXv0WiWnMjkRkz8wZqy7mxM9A4Jmd9QUyLj8taWVb0hEAaH9J/9itMPXDhXTYBWg4zPCLiq1Rgwt799QnxrJjhJ5u8ykExyNYLlDO0xANPACwK8oCYMUGjy3WAbBi5dshAJzwNbMXw6D3jMhOiKYpqCpNjINAdoybpWd3gA+oG4aKggqpA8pRA0Wgauz0g/TF4ken5J1n4A5mYJftSlNoRoCe9B4uHrshXl/GeghM2kZb9bBazASQAMDlXxWTFj1N97jFT+t1hswZDwCjlyuFYj0EAMDNQNsY/lBMA2DL2yGLbmsZZzQAGEXoky+qEu0PKa3EdlND1ffZpMlgYFEtAZ8NP4bGWQseoDUbNP9umOIQIX7sk4E72A1KIKP7HagIqVgAQBpmFvE7Z8LKTFwpWI2jBIYJkBE9wdBtveKhrxoDEc/Kl7jMfJ+ZgfKy/WAq7AVaepm5QKvWMG1zb62ODwComKIYAICqh+YMs6hJz1xeyhB3qLYmSS8QuU96nQvRKSsdGlJZCDlAED1wNqvzpcSPzqXv08EbzOh+isq/bNqGKvhkygp80b6wIJDgFAD1yXT+PDEgqwHygBpSr/VEoo7v/w7FFQZ9Q7IgDDBAPZgJ/BJwSfDEWymxDIAuAWcWSQ80AHKz4P0fEUzyUZkd5YwqNmp8ki1jx7R4ylcvyqZoDbRX0Q+hOqLrRxU/OkM7u7sz7NmztXz1ltQWTlRb+480SRPCoWFFcYNhAwsohlJFhl2zNXUSaieJg1Zo5CAAoHDUzA/HgTwAWwDeltyOARDDtaBtAgCwqSAI5+hJFAH4PDZhBgA33NiOyY0NUm3jlWolotPVDVc44U+obWcMD2+YV/xIINeeXbZ3vwO5IFbLw+qqdyB9eEVUEC7SaDgFoONbArPmyzDDKKqpJPYpwp+zlSWZc4EldANV9VZyFW0CMoEsBWTVGqQYmuMGAKaWi4wGoHvBCVfVCqcpmMtwSQVSgWTDjFRLIBKfZbiuA1hhBjCqyFHPt+bfmF/86Oz43n756Y52mZOhhQIr/HtgiogHSPB8YFQIxmMh8ndFDwGAapJf3T+VlCQtfwXj21WERk4avO7gCa75iIABhNyakAT0KtrT4gUATCYAwkDaj8Jw0fBYcJmKmJoc7R+MwtcC3elWQnuempneUp1/fVri7Y9UyhPP7EODbOenge5VPM3etWtqREwG4lpCdf92CSkA8ALmCQEyqq3XZ/nHj3xKnOILFRcgEeQJtPPOBkW5uAHR21A9pDGWp0Rtdwld+na00QUBoAGlZsaFoRjIwMnw6OhAXxTKNmyf91AUytVC6z5QtOcWlj4WUNKl7//qY4fGG0h/ybPsXfnTwnFOLMGXYevcRHPGwktMYg8gy3pzRxSwlVxJcvuQFwORCp9a1NZ4A408AEjCzTWDrVoDb0FjQtwAAAeCTqWR9iJGTnvhoMjLx1wwJwCgySzFBwYyiL6mASa2qXUdIKXbDxWKxckfTvKat82mAjUz9+ESkAWtXwjHOfFtQ1AHvFSNFUB0jgFUFyACkHii2emHt25c33ULdItZVF3St7kbuAVpDqWabqvjG8JRh0hrTANgRBTUD9Yi1WigXZ3g8YmGa6NJmpUMAHolWvfQjH6CbqsKWgwE9JSk9t8sYS38pYmJa++8//z773fC+f775++/c23iUmGkfFplFO7M0tTNXQYAPBQUA9AYV7ZpZEDx/RgTAkT3ALrH7oY/uNvrvXQd8lJwxopnW8VpAABAXSDIUROVMBojiZ8Jsg248eOx3B6+TgyAZuiRqVQDAJAPbq4ZEaVicJddhZIc7uySCALg+up6CnssBKGrV9y6m6S4MvHO851Pn+XkQgn4cuhAOTgz59nO59eGxDKqG7cY8YCGwueXs4EpJhglyXcNgXqYZcaMRK8CpHenPxQGfSD8YE+bJfiwutqK8gcwl6BJRAdBNezCYO8MX3W2TG7mx0KtT22WzWyNaQCIKJ5mYE1UEnKayY37hGq4Qo06AqCnnhqQGBxQBKVVKNPCoB4CCHtXdl2/df1AOgi++zImANjThTXhbvQX9pynzycETkJSYS9FEQVkT8nTy7lWq5AVyLEBoSGwECuA+UhG3fZ3BA9aE+hpg4SwzujiJtTtqr6FzV5o5CBB9iRNjnKtAUrIAwkaAdLGZRfXxQ8AZDNNZpPBqPdgP69WOM3RZtSi2RCVamO9xPQoxKg30n60yxUpALhs1fZuXAjKQEfspGVgICBV8PR9oR4IUKSaDF7K6s60Wm8daY6gbslJ7xDuCC7Oj5YDyujufp9/vNm5S8NXSUpvJHSTU8XcOBkYVt4u4xcVYQBMJnWM9nFVTJgaL4j7ttbKBmN4XHhKOAC6WpplxwwGPDNMNS4sCJvAEOP1gmqp3U6otKqnXDUkBWmUK4ivlTdPmkbAD8reOSHI05M05Z0A3FQLAcANElaDcWbucVQPMNPe/Zx7sKmbu6xzQQszdMJ7y8oBIL8ft504+ZGDBB1MahvmekM0lNEjMPor35P1rYwjDSAbHJQ51OoaXAluTxJ2STuwE4Bypa4+ia5QMN8at1dnJLVVd8dQ0/7C4kcaAozB02s8Ahp0tPs55IbHrDd5AHADoiFD52K6QaLyQIAS/j0n/htWqEXc9er0eDn9ELDImQoCTBeeapHxmWfsWjYUqnuaTGxRE1xZwZVPSWz8n7E8KHJrOACam7qAncfccVtdp5Dxo0UEewj49d7IqfqotErWFwYswCfegRSAsFCTwVkCzhxgclh6ZmZmRjgE/AOXngIA8gAA7XzLOcEnARkmWH6uPZr8n7LK/6YVRYvWGzUaYAXBrIOHIH22lQgiDJRjCM0bMlCeOnqghZsURfdsFkg8JWFDQizvjFnnCq/t1o6bKS3bJeQTzmysUOIdHOVayt8pRdo3kD1QgjdqatDwLmtuyOBnZIilD9SwvLHdqFYI1cKxsbwcO0CAMwSbJzLsGdDmfYMHAHtVCZKE/QD58/OAnjH5n7vF1dhUWG8PQauwmtYOcfOk8FdvQfe/iWeYEJSyymXp8DNOpxMMgqgdfNuGtaviCgDtTTJn20gj29jZLpqqh6ZwOrVkMOKboERgQMN7e3Tk8CXUs8MywUTSx1/JzTrI+HHFu/AHHhloHUu3f8/lhe3dmTDs48aOdnHV1mDpLcFzBqJXgbszMYxmb1SzTQPW4qlRSwF85/QuqwAAN6AYEJo5iVrN/ZZ6tsZZTlCdosTP+sSYXhy4/e3wmN5U22hq8zIAaBTO60L75MvRLBdq0t0cydkFDYC2eZMNaHQPpwBYyeP/QOln5wlGO4YOAkF17jVPFcbA+1Agzg0BgG0YIWhNFTNopjq6S4kDwLl+/kny8+cClgJKPXJToAAgDCiZ4doQUN+I7tukXouW7WdwGLVucdiXFtMLIyIBoGqsNRm9zLvRVeUTVQM0kA0uUusH6lojEoFqyNI0VIG75QZHvXgsXaT4MzIyQfw5wA7BNz78MI7drT6lCyPg+eXu3DEOAIhnwDSE+w8j34IZCSVtAHai776VH7rs+dZbftpID08XW4VPdxtG0ITmDWmu1sJMCYIpcqsc1MBmsdOXENMA2BbZ/2Wrbb3KAkDVUtUlLPipYUCQTS9vqwuvB6IZi0ajx62/GpxG8syxC+8+/GLPyELDHLHZZ4/VKtIG1Tf+5KRHC5MwAuxjN660C1sGYT1EIaoCFUdzANMzu5/tSEracYO7/vmoaFh9o8FIGhtECgDiwMO1sjJtARysW8pHqhxXjQ3jLJu9N2wizNZYBkDKlppIfldjbZm/nZvv1iqYDqJVgw2A7rCCmiZZJGtfr61zWSjPTSseByZy/qABDMSfb63e1d9/+/oNODdvXL8NXxCAwHrh2jEL7cKJoe8vZ7EAgBqDgU0CM8PmozmAYGEmFElDt5lMMZL+7WK0UmqizdIzJ/QAUCEBSKhGLgpUk8fqvDan0T/OaAC1N6z2symm1wVseTMyrWvuGw6yRv6EoklEy4G5CxDxUQ0uVTglmEBZfJ9zeAoGd6BxIELnz56NpgJai288nN2x40hJEq4AluyA6tzN6wgFDAiKH7ZdHZ7I2XkpSVHyLJ0FAKupCXp4Bw4BpVvBuAzA0FEmTLTmX787tePKwxvW/rkOnV+sABAAWmz8UDI1VJgnZaVUEKc+TGDDYns6fAQAJAh+zX95i/tqZ51NyPxHNsCpJANhS78YAHQogk7vXTQPROgCQBUgD7zC/Nt3ZyOr84XJl+Zu9jMQsN799mrD+5cvPwNXfgh2gbVzExtASNAMxAwakbz8yMeAjmDFRNYYE1RcnzvCPMP0zWtt9XPF+REAKFVya8eNV30KA2w1D+Lslo0ejvldwaKzcrMUw3OmjzP9FwWbXSAHRMD4rQoNOaxojAQAfHVSM4TuKcrUhuSfY0XcoLmSqGSAS3dvW1Hq4Gafduh6t/1yBjjzdxkAsGUgwhKc7bfuikIDxUameyLpnYxcHE/k3zrCF5kKk4f/dMMa5nMWz/qu8oUAuqDKUyGzsVsSK8jAhhXxBYB598KoioQ2ABwyJa2ygbJPGgxjBGoIxOXqGJ0DH7yY9wGx/NFQ4Ll5KSGFOx7ehvLR9b8Nz+VnQT3n8tNLirmpZr5/Vw1DW0CNS5aAWCfz8s5kCB7QGPrqfiEPDf50qb86XAPMerlNg5BemFQMI9Z7L84Flyo92+MKAJs2N86/AKQlqUvEzVWWm0ijsc4XqQHoQFUH9rcRW4t3/7LAAbtbsiAtcMcta3X/v74HvSQZ6Znpl7MmFENFHGkHCsz/BgNBJAfN8Djb+fRydxa6/7cjLM3sDTECwASEAEDRniTEdasYRZuSVc6e+LIAKzalLrAarFHQogOjFw1KAto+aK94rw/SAAVkQ93oBJrdhor1nGAOQtplahG8sCTFdH/1X/8Kdj4bhY7duD24soAZGmIZPQI5QKlGsFCeIf1yei5gxHr7kgQREZUCi/kPKAd6dYbQ9sEADmSHER/SdNUPFiAljgCwPnWB5YAqj6A9BO1WVDtI1J3VHja+owCo4HUNKFdXDHxdTv5j+fk3Ja5/yZHDV9A5LGoT+vX/Q02/B3HuIOcGMHdZ1h5w9NDC2cgSsDDShE+yUO6Xuf8lV2aFj510V5Ad2pV/4Yhfx3WG0w2KYzj06eiEeMhEubfFFQBSVq2ZmR8AQA4XzHzBO5mB+zWguBhmAgoMelfVyA2hBcgEdm5+BD3vyvRX36Dz+Teff/7NV198McUDJPWvaKJcNc4ejd0cMdsYLjAQNFAKIG+e+89AoBoCf+Rs/P9TX6CH/2K6hNcvSQ8FqQDIBAY5AJD1XLOg1gsAMA+g6UDxpAEWBkCXYJ8yHuJAyAlaGbZhFQAANCIFzrhAsYaVSHZ1frj3N/vV5+Hnmyu8DkAhRH4uqhvk3OpkcwCQArh0u7p4YfnnILaXQnEYPcOdO9d/un0n9MhJCqgHF+OiA5iA6yWjOjYKoP0KNdsR4gWv0+SHLFBKShxBYPXGBTdE14ZmBbEt3AXQBCpyAsAHUOvVbnaVaw4nmrFdD8UG+Qor/jt37nyKzp2vb18HBIT+HmaK4W8HAPiYeh2h13huVUtUADPCDuoGu31EMQuK5fNP7xRDpurLU1+I/AC2AAnVwKRhWs2tnfCz06NLO8EdLq3awsg/bhCQkrjgjvgThbXhczqANxnKBKhUKrR9B9r2D2MX0IquMJA9YDlYmPynP78DwgfBn/qUkf+pezLZva8/D8kJOftjCADZt3x492cBofNP5wsSgBnhDAP+AOVnWnHlc/wMd7LRK7v/I8MLxgc2DBUzx3qzsINipgfpgAvEJjVVqCdiHAaDpzAIiBcIJNYuBADhxuUiZvoqrBUVrPthAVAPwxtxVYeRP3gAd8Xyn7pzB4RzipU+AkA/+uaurz+/wscC4AZWYwDc9ZJGhgd46boV5oxkMGTSjGjHng03W1HyzZ3P8SPn4V1lTw8r3nyTe3o0sAjTEPLv1mnxtnLUwRLkJ4Y0gVvjXweSX83qgJRlALAqIEQNNJPMEgZSXxdaJ8EAAAhhdzEA8hDXC+TffVNs/g/D/b/DiJ/TAB/hb390J6QCdty2Ih8SAYA2FhQQtLzmlpWZMyOl9xlnMxMBIC+/f1bxxR2sYT698xN+Zc9mFW/P8DMCYGQZPvkPawgEZAJyADWhuacwM74L5gKsXo0BEDd+QFrTggBQ1YbWu7E2AMa1CztHgSSgJqFea2VcACR/mPxzRBz6TYP+R6I/xfyCxJTLPP5P3/CRQNJDKzIhAIAGnaEAhgL7p4vHMtm5glHEj58uHXikgLHPWefizH30yPunFVWNDm5I0NSufAyAXdNevUZtkBuu9ip6QhNjYMZtKyQBVqes5nVAXACgRbYIFdAU6gAiuDENF8UjfKBpQwiA7pxZcaJHAdr561OnTn0a+mAsNbgBn/9v6AxiVcAFaw4iBT70W4gC6De/dHssN7riz2TljwCwaxqcjM858/ITQsBPoFpmKh2d7Ku4yaqAKT8J8WyBDjxAulTYFteyBSmA+FIBiwGAoFEc4oAChkhZKGQMQ+8GzQIgPzsDG4B3hK15f0nGAEAOwCnufHrqznH2+69/pShp+fdLGCm3rFkYAEGLmqC1sHE6uvwzQwdCjtsliq9YBYAdzP337n36lUJRW1rqYK3AbDGmJF24NKoHNgClc7n1dKjYebG2GaggDADiCAIbfIsAgNndGVrvxgTQuhHhJFngCwIAbjBBABJM906B/F1l3yZxABAe9p7CTf3msKKzohEnDaZQGGAfm+vVqYFk9gXQRaOLXQSAu+AC8goAmZiTJ+9AhOl3lleMs97ozfzi/uJd1y8NEGgOSG9Sva4sRGxofOt/gAu4enUIAvFgBFISFgMAVSO/0p2l6KBs8EVRdzjdwwCgGsSRa88StHz5Gm218NtXd+6cCj93Tt/DTsDnswp3WWXzc/AFSm5nQYPP2NykTk2PTu2y28Wyl5Y/NBhUTylmOQWAsFWcCX/45ojC5Sgvr2hhAwGIAvrzb9a0EUoNcMx6ryoFC6Xa1ySs4gCQwpqBONAACZ2yxSCghe/WZla7EDDKp1NEGOUAsDsjMzfT/n4oAvQ5SkuRAL658/XJU+wHHPgNKYGTBx49yj71+bSiZKbUWfoPcBxvgRNhr57oocmOoetcWSlzodNdfEQxfYd1MtE5efrkqU8RsFqdjtJWph99DuTfn3/XazSqKSWaZSBcdNyMpgKkrH4jzozA2hHVYhBwoq6WS/oxLE0YJShoHUUAYE3AGAAA8TM5+Xc6nOVOH9YACACM9NlfEQbOIm2NAsG/OR2O0ouFirlse6bdOtFhMY7cghmjSL1HEXou/sjMzUUA+FUBQeCnvI8BD33y5Kd3phSF4/C4FQGmJrQLTED+nJ+CCLDBoyRFWy5PvAk9gClvvCE0AnGgAta6TIsBgOxiEteowc309Sb1CRdw0QM7biDafl5GLkx+5OVf4yh1lDtHWA3ACZ49p05+evrRSQQA8Nb+VlrucDj/ljSVCwD4aaL+qn8ux76Q5OEDn/RuqDp9w0ufe3wAgOLbUnjYY7jn4BaQUiEIGKWAvVxXTxNa4V7hdkQHBwXAIyA+NMBWT9eiAGD2cXuc2PVe0FJbYxNQxkmsAaAWjBQAL/+k1gp4+xEAIEn3KS/5M/DBSwlZAjDWfSqTrcj5n9d2ZNlzAQBkz9RYt1Do6PB/CD/27Cl4hk+F4ofnOPXptEJx0ewsc5Q2YScQALAL2sXIq8NVPTqDUi8Av+rEm1sQAED+QisQ847g1prFAQCSAX2CecpQSB1ITp60hdhiVEfVTUwHy0y3h9p9a0H+7c5y6Pg4AmlARiz84eUPxvoKk1u2PXo28aw71/70Wv3Q08sC6SO5S8sen+6fjkAd4FNO+uyznPoUTMsglLHLnK2gAi5dKO6/kH/Tpbw6UAddjITWKfJzfNsxAAQ6IB6swFZ30eIAAD0CjcLB3Xrtm6mfkKHoUF9fdxMV3HJyu/N4Vo6nHAywgwMAvvhnhBDgMICMdQt2Rh5lZuUAk+DpW299DxwfiQufDR/M74LPs7uhLXj2DuNfCp4AAQABC14FKKGhA8UX+ndBiqm+sOEqBa2O4jXILVuRDyhAACP+lGUAcG8Q+0+ZkZ76Y53JSb2cDi1SQk8drrhmQVsgrwH6wP62Mz4AKGih5IUIALndmWaIGfczsqF7ONO+M/X9bnv4lc+OenJR3mEKqxhO/mfPnD175hQLAFVpOdiAiQ8BAP3Tw1qva5QGBVAWVvSCuRBC+WMErIh1BKyrO7FYAHSx06OZkS1Gusld4y7nUoEFhLrmFqMBsrufsbl9dyP4dY6yUtNfUCKIBwCSDToCCEAYgLfU3c/EN7v7+VCufVGi5wDwPbAKEQDYZ2APAkATM+y8T6G4llV8ofjJRE9D70BQr1crS8U/X18CAwAOAvERBywBALL2qkGWGmjAM12rAoWToTk+es9dVHHPAmmxc5qSfCD/9jKnCbjFAIBTItmcxVf0DOsOQtIWN6ndy8YASN+5szs7mvBzuP+EAIDJMNN32NvPPv7p02dPIgBg57VyBvqOc/ovFP824u21DAQ0lMZQGRbopKEo8A2REYj9hOBSAKAar2pnJgUQmKtZGPDVlfKJAMr7EKXaD+Zm56R/dAkDoBZsb5nTjMZNQR7g1JmzZwXy55UAuraffqPAL+RRDmvY0yOFnxM6wj/jz9F4gamvBeI/DeI/fhwDAPsWttYdiqcIADeremldh7+NVJJh4w5nErEPyMj/DWEomLIMABYBTfiaMrs2KNLjri/s5eNA2o8yrcXVSFLMuIekQQBAuQlXkyBNc+oMCIWRDrqdp3kInDx95tQ3isIQALLB+ktLPsrJtiMf4OszZ04fZ6WPjrX6JI4CcPri2dCOA1n9F/q/8lA01ebvoIVr5ZmVORtTWAC8IfQCYtwOrKtrXjwAZOZOPCUUj4U0wH63+iC33KlUSwanQf79ViSw3Bw0qyP5W6ejrJKJIEA+AIDPQtLBh0XAZ2c+/fwISiya7Tlh2l4k5yz0H/7I4v7A/o0dRQEAgM/GToeeYGzsuy/Y4fCq+3kTQx/mAQB+nGijKI1/gNY4w9DdvmYVDwBGBaTwocAyADhv31PLJ4OAUBWgqlq4JXz6yal+SLXmY4Gko2pg4bfOMuZdbmUAcHzs+FmBgBgtANe2GgMAzMt9EDkK7CKufhY+7G+ig8GQlQ7TgQ4jAGRamcc/fvz08Ywx0ABVzM/3KG9u6OBH/ef7f5trI/XGwDApDw9/mjevT0HLYVIEyYDYTwcuDQAqVTseDmEjlHKCNNa5daNJ3CQPYmDoAg+A3GxU2/223MGkitqhGDMLAMj5TKQAGASczB9DPgA8kOpelqTez2Ju/DwnMw/Kj9+cOXvcns2J//Rnj8ZOTTO+pex+zv6b7x/cjwGgpIxkcJSkwgcdFb3NDgRkELBamAxYBkCobF41zvbtGy0+Rb3Fww4UdmigeAf19l2MwNJ/QmmAcicXQQLn++uzp7OrAQBIOMexiLAROHMmawz5AMgEPMrKEUtfLPa8rDx0mF/R7/zfZGcC4L747uzxzEefsU9weuxe/qkpRSdOVTyyHtqz5+iej8/3/xigjDDUNmh0RIS5b/PzAcXJoGUfQHTG3RdhyhqaDg6jFYJX2U5hVammzXUjH3LtWGZ5OekQCgbLuUgLyoFHfvzudG4uvp/8BzpnjturEQBgOLDZniWh+BnZz3MQENLB7Zw+efZ4zr088P4xvuz3Tn99RfEnFATcP3jg6NGjh45+fP7CV0HoC9RNBjSlESPvO/npUCnh+cDYxcCSogA2Xk7uw63iBRTl9hivskagUq+EeawIAPiqHszcv0PR6eQyhcAIKQEA5KD7ie//cU4HnD2T9+gzlLKDF3I/UvZiUR8Un7yD/N9komLA12ePj917xDz+6ep7j76DxgBMW7HnHziEDgLAqM4AE+H92opI0sNaAQA4+a+IcRWwdekAkDW525k9XpYAzNeyuDx4pWCZGuZxQr2d09CZz5Pq+LbCcUQI+A608tjZ48KDovVHdogCsLG+lxVF+gcXPDko7gCIjT26j5/i9PFcc/bJu8izgBCgeu+hA3AO9Z8//3BYhzacNtTbItnPoelAwlhwGQARRiDVcwKt+IH3MSlI0hpmsYTTCPMYgXGzOwtL7KOsj2aT+MbDZvACp7+DW2k/ffp4SAEAAM7svpdz/MynUwrYXJCRJ1D7YuHzGPgIDv6FOTwCcr9HTsBpa8b9e9gDqL5nzocgAFW7VZm7DhxgAHDhydyAjiBIZUNH5NaLvtBwkNVvvBEnmYAXAIDq20AD7IyA6bBG0uXRQ69wFRJ0ETU8dQEK7nuy8rCcduf8mvScHy0A9cDZk6fzH92DOECsAh7dGzt+5utZhc+syomw+aEr/tF8B/4+69AOxZWT3+UDyTAXeZoZ5ntnIQhAUeo9614GAAcunH8yAUQQuZ7y90QSYQYTRRoAGYEQOTBWIbCEamCoXbTD4hspMmvUBfRoUgeppoN15WjAFoQBiHGRh6W1e/dHu6em7vFmAyj/X5/+LPN+jtAGQOLOes9effo78NaaZPfyRNJn5D6P7HeLPssBr/Ob7x7Bjsv7eafBGZTlnTp1BbkA5iwk/73wceDChd8mtCQhN9CB0UgAtG5cLWECYjsbnLJ26QCoDNTTuoCvq1JL6JV1fqBvk140Pdip9d7Mh2w7SGz37t0ffrg/69eSp/d5NgGY6NPgo91/9ChfrACyrWe/+7EEUraZeeKbH0XsEgd9PQvcwNk8mfnRo/v3H9nvqe6fPgU+IHAYHu1Cwt+LzoXzP3YaKUJOWHqDkRuQG1NXCU0Anw2MaSOQsFhGkOB98ivl1NVAZxFMjqQDdWpKTardAZWskvJ/tQuSrXsOgvj379+/Z/eHEw9zWQQ0VyEv8Pgj+DRXqAKq7z+C7NBJEFWrKkzvi6UuJfkP8dn9If7kYN47SZ0yc95Y5r375vv37+ecOvlQAYzH+3s+ZqS/98DH58//3wZaj7pbh/2VkdXO1E3SGmB1DPsAG1y2pQLg24DRoKbIQGezU0nVA7kK2qzrq2DUlrN3rr//woUDH8Ht378HEi8f7r194CCDABNwQqbOPkIxuVXoBDy6n5l1/OzJrxSFzfcORrn6Unf+Q/FBX8p6qui9f/y7LNAx9+5BNHjqJOKZqXI+3ssdAMBXfjQfiqA7GiJ9H8wKFALgjTgIA9JGzEsFQHCUQs1hZO9Ia7kepobSesJg6fnkW5lpYOJ88YULxR/txuKH8+GHh/bmmLlMwAR4BPfBBhwPBQIgrdwxKAZOKzwnMj4SiX/3vAeLfT97GAgczJur8X1z+t799FyYGzh28rtbyAI8+lgEgC9gsR1ubWuInI/X5douSAS9EdYiEKsA6FxqDFDa0EExY0ImXT0Wy0DdgAWlhSdrWmXk0G8AgP49HzLyx7mXA3uz7zP1ILcJkv3Z2ffM2RwAzlrvg7TyT59BPuCjvI844e/evQjp8+LnMYC8gOmzY9YxNJ329MnvZhUu2f1DWP4f70U46D//ZHrUwmwJkViBbeNTgSkiZlBMx4GJviVbgAZCjwfFGCwdrqDO4m2w6AnUaudxmv7tKwDAhQP7eemj83EeQkBRXc34iZzjx9PvgXlmdMDZz+6ZH2ELAC7Af9l3L076vPbfH4aA/btz3keOxnenP6u2nv7uO1gfOKPa34/lDwdAAFHgFGw2xJOHApGzccx8KjBFxA2MZQSkJLYsUf62hlGanbJaYGnzBiwdbhRZAwICPtm3Uxf6L5z/eP9RTvj49O++z9iAL059N6a6B1663YoAUH1PdS8303r6DLgAVc+yWeGLDfsC4t+DDweCvINDipIvfvz6x2+++uouUAHcXfb+j7H48f8AgN9ghwzBrIqTmIxRuyEUBIRrgBgFwKqNTUsEQL1XS3LrltQ6jd+vDwTRNBdwCvx9jqHfAAD9e46G5A/R997+D+9jTgAk6w/mZGWADrj3KDPz0X3zvczcsePQxDetGNqdJdLt0TDwoQgAe0IHf5rzFLWX8pMpdqZj3c+f8wCANpJgtoW22KJnglZHMgNjFADrU2uXJv+Khl5+zCqeFdMb+B/vIkgQBKkPBt69BQC4cOgoK3s2/Nrb/9F9WZEbJ+tPf5ab/gjHaQAD2B/12fEzJyELMJdzUKzVBQgQ4iAqABAC9nyY9T4goPDK7PTU7Nz7vx498LEIAefP/+iRozQAOC1tvsgy6AyXCVotIIbFthO4aXPf0gDQ41KS/MI9PGt34L01o5SB0GgIkupNnboAANjLAoBDAGhhQEATStafOX4cZsBAnIa1QG62FRTAd9DA9Tw70qhzGJACwP4IAGAE7N7/0/XbP+39+MChA0f37DnAmn/moCDgRxdFEQaDhjAQvkgvsDV1vZQGiOVW8ZWbLy5J/k4v40WHjpFUjgbVGqOT1Kr1sDPstwvnz398SKgAGAQcVLWicgB4aMet2czWyMz07PzTx7/7DjpDS37K2rNfZM9DOkBsCqIpAPSt6AMcCZSFAC/0wAHW/ecAcOHJ+a9cFAwIKy/TGqhAJPSbuXVBb0T2B8QoALYkjy8pBhz2wg0SIwDErtbAYmlbGewUuhr8CgBwgbn/e9kAbC92xDIfgQ344uR3xz87/tlYLuwOTgf7/913J3+cRp37efvF5pwHwO4wFbA7EgBHRTA4yoQg7CsQeAAfIwB4ab2aqARKGznZoopsgt8iBEA8sILWJTUuBQAObw83ZDV00Log1GhvIpUE1TbxBNmAkPzZCAz+y0btIlN3f/zxa9ADx49fv/711z/++AWeJbXjp4+Q3I6KEBAlGBCogEgFsIfPQHAQxC+gn3EBnpz/ooE2amA0XKnW2OFtjtiO4FknTAPEgwZYW7gkVvhoAymXOEpm2p6NUhaQ/lvIBvAGgPfC9n685zo7tnPHlampK4dLSo4cYVvIZn89eDQKAKKFgvulbMCeEAAOhGmAfhYAfp1cgxegqrXe8UhS2FYpDRDLAEjAxUDTItPBZa4BUh0pfw1RwbWIKsme6fPnz/dzGoAzwXtxPu7Xh0ORw+OThm7u3Q0iEyBgEQCQiAOlAIBl389ogAtPnjyZDugIpQPp/nKNP9IJaEkQBgHiMCBGa0EedHcrFzclRBX06w0R4jdoQvxKoAkpR37DNoC3ACErAK753qfvw+awkhJub8DQw5u3D3y4/xCi7C0eALs/nE8HcBaAeQnoYQ/sZS3Ak6leHcFMhjI7eyNzYE1pokwwWw+OZR8gsRNd/qJFVQRVx2o6Ii2AQS2cs1ChNga/wDZgr9AJ+JipyIBQ9uw+9PHHt6//evPW+7d+vX5779E9DFtPhABhiSd6PpiLFrgsEAcH3gU4cGhP3r5HXz56nHcUvQxQAE9mYfiYnC0Et46ciMwE8Z0h4azA2ATA6jU4EVhRuZhJUaYGH6mPNADaMuE3l1IdQ0gF8B5YP3PYmhyS9dE9+7FnD7HaAVyjOYC/DAA4NB8APmL/F+mAsNzB/pAJwBBAmDp0gIsBnpz/7dIAjXeg45+nJWJXwvia9aI8UMw7gZuYRGBphXkRCGirqacl5H9MbD+cxoYvkA3ADsDeA0f35+3bty/vo6MH9rKS5nPECB972Voti4BwALB0n90S7AD0lwwKPpQEAH4G7P31s2lAAMCP7jZKyY0GU/U1RcxDZBIBbwg1QEzPDNySjGtipaWLsAE2r1+QBA4LAIQI6JmFSJC1AQdACT9+9Hhf3v5DjJz3CgGwl9MLEgDg7n8UWthHErQQFgGhMPCAIBHUD/I//5XHaGR8QGwDOsPpcO3JW4R5oNgvBm0vbMX+UPkiqMH1NW2ROQCNMZJSWOH7ilEBH4vyMNxNFyFgL68AGADsEQBgd5j8MUH0oARTSAgBoQoQhiHYAjyZ8tPy0HTQrs7W8ESAe7sgDfBG7E8N3Yq7ekyOsvZFlIEDFkNBuAOoqZD4p60hFfBxuBMoBMCBvQJQRCoAXv4RXSCSZLEQAkJhgKAYADHA+SeHJ0nhS24Kr4R1edZGZIJj2gdIwF0BtrKCxkV4AG00EQaAsEFr3D0yjaB8MLz7IRWwN6TqwwDAfSYGABZ/uPTzpGEgtAecCmDL0SE11A/3//zdqja9xhiyWa2dXeHzDxIi8kAxrQHSRnAagK5fsCJg8gUsBXDEDqDDLNlDPvN/nuCCAF8KYJ093gU4JATCIfaw8mdvP/b8WMFHHg4CYcqAdQPCVAAmhKNXNNtAGYXjYbtGWiMSASlh1eCY1gCrNzIDP8iehZbHqVpr2igiDABavXQCSWVGKuBJv9DKh112njDGSp5JAx7lxc9LP3pPsJQqCBkBgQ4ACBxAMcCFm4oBWq4tF8q7NgzBtRtXhRGCYnpsNEsHqaQmv12oCuD3o01rIgQoNUXRxkjMzMKFO79XjAD2+h/iAYAD/1AFgFX+UtLPit4oLkQBbkdhVAAPAOY1IA/g/BUvqdcohV5LRBwwiBMBYeMiY1cDMGwAVaU+uBAAGms6qAK5yAZoJB0ABgD3R9iqcJjwQ/efv/UCCsCHossfKXrxCUEgj1cDEghAEDjUjwzAV9DKbBCHrabwOGAcJwIkaoGxCYDtSeNMa39gIQAEG/QoByAAgFraAeBUwJUnbFEQC1+QABDJn/P4QnVfzvFbSPyiDtK8MB0gzAaifCBQgc5f+E3ht+jlWvFwuNqwXFBj4ZYVYW0hsewDbGVG/pdq/Av4ACdqhmkDAwAOAcqyeSpIEAh80c8SQ4TunlD84f7+7o/Emj80BSp8KFzY3BgeBIwriHLMrB/Iuhl7kTZ6cqSGIgvUYWqrcURsA5qrtgtqQcL+8JgEAFMMljnbGhZYIl/rMVByEQDmlT9yGkt+Qwg4FEX8EeEeH+otIHyJ+TEhZ4DVAYJ84CH0tFj+VwpRHktpsIVNwb8YNg53rcAFeEPgAsQkABI78dtRXu89Nq80uzy9OqYOzALAEJkBjiit/x+gh57vP3pI5PgdPXroaFi4x137MOGLh4KGZsRKgkCAgN0hBBxiIIDl/7/BAVATBeHTIVWDYY0xIxu4YiA7HiCWNQDbFKBydHgd848EqdHSBUIAqLWlCyUOmpO+ARVw/uM9hw4JAv2jgusvEr/Y5PPCl5oMncPDICtkDoQ6QJASBi4AguGTK4oenZyQq9XhmctmjzgJ6ksTjwxPiWUNwHLCTbqeBuf820ICFpYIwgJA61i4dtB3+Al2A/YIZS/I9rPy5z2+CLXPXnrpE1IFWeEI4EMBQMCeQ1CW6P/tSOGAzghkVs0xU0Q7mDgd3AStARJ8sNgEAEsJrqQnG+adEtHqhiywEABK+WKqh67pC7hLZE+Y8DnPXxTwZYVr/oiJ8PAfMIkj9AA3QjJPjADsCe7ZfxTJ/5bik3qmkKksj9Ru4p05g9AaECb+2NUA65gdIJXkqMS0BKFa9HMKgPEC1VrnYoaK9iVNQ7c4NIrtPxrWv7E/POCXuvyc4PnNIN2Xc3bm2CXmh0v4AUxK8MM98PQXYFKURseEroZKiZ5wkRt4ERIBglJwbKcBEty4ClxEBgO2eZNAbbRaAIACZdliSKRdwcAnJbf6YUmHuIeTDfm5XD8v/oi7z22FYX6zX855vkMxAeuh8BfEdiBkBnBCgKWMfbgfBtY8mVU0kLiOTRjUUpardkRoFlqBESCYF88bgFhEQApTCpJVUP5e83wegN8S6gUB+Wv4CGBeGlFrgDb6FbPQLfoxx9+TTvgIlX827/iJt8Wkd+c8v6S49nQOtgpmizcJCCDApYa5PmMA360SRa+OoTITRkLKdW12N4p813UrxJXg1TEcBDC0WKehYXI+QdbUU2ohAEKMCtV8CKgd1dGWDk/J9JMLe3nSxm4BzUNk/hntH6n5WfHnfn9JMfS0+3LmO+9k27llUcKIIOQIcAj46KNd/U9g/mSHjn35BjUpmbtoEbKDi6rWrghNihYogBhEwPpUhhhfrmnomdcDEFGBC4QF9XkA0OyvJ9VGHTVaVfLVeWveRx8KJ3oJgz+BAshmIRASPVoblt5t3zlUMrEzA5rJ7JlP89LFq2QwArIidMDBvD0X4PoHCrD7zyQvnFF8XEFzjK1mg8AEpIRI4TEIgJWFOAgwO7Teb+fLAdRTYhJAudDTi/p9g2AB0CAZWusvPHyrOCdLxO8Kk39Y2C8QP5L/s2uXrj2FCAB/BkvFwlZJ8dGAMBrMy67+bUrhHtCFXrwmWvJqRNgh0pmYIt4bFrtO4HamL9BU1uaKygeB0n6ANojbQCoXA4ATDQMMg5CgdG1exeyNvNyDu8XzXXn5M5s/snPC5M+sjEzP+X7inWeXsfiZtbGhZYKigFAwUB5GB2cUP1QkBY20nJBErnj6tTASbNm4erXABeAVQAwigJ0RaaM7XNEzwRfr2kQWwKAFUq1qQQSoeny0kesdtViGP1FMPc3OPhiW8hf5/+HeHyvw9Gffg/gzckWrg6UhwMeDWRlZNw8rvPUWYSObCLnijIVHEAnWpr6xQlwGjOEgAIuqkhxwRU3sFdUELSIuuJKoFEo9GgAcDYI2YjVlIYKwFvyn7Jw8Ac0jK8z9FwV/IXFndIPtD1shzq6UzA6ZAWGZMDdn56yipkdnMRJRG1hEaO0TRIIXk/9lRYgJEMttYdx4oAqqxxW1RbjPbSALRDTQciR11QIAqJgE9p2wgZzUaQOKkoc/5WTz6z6y8sLC/xyR+uchkJ6ONsjDycyIhEC4IwC7QzKyn04oknrlFoqIeOVRq93jgkTAv6yI8ABiEgDrU5lBaRXkqDdaJviEe9QiagfVqIuQ/FV8Q7EkAlRlDcOiQTLyAiMJM/oVJe/8lJ2Zw2x9yQqZ/3D3T7wsHsue+UW8RD4UDfI6APJET+cUVf4AIbT+0RnMXAUgNC2xERIBq8VksJjtCmJgX0oGG6IlAmtrSPFAELRtEcu/wjSfCoBZghFNRAaS7vAqFO/8lJ6eE1nzzRYGgOh+C8QfOpKOQAgBmd0ZSPwBtTFQry8Ib2GZJ9vZXscng04Url0RBzkAVAlgZ0OU04GWKOzeE+5hi6gZiFCyFqCo1DxPKsDkm5QYJEHIYWUnhoA9PUdw/QV7YHPDNQB7+SUxwFgB3g/Iyei2g/KvCmgtpDLQQRkIcQ9bWfSQVWX28Sqgqy5hBd8PENMagJsT7iD9TeZoRCBa5Eex9XQVaiedzwu4yA4TjYCAEUbLuhQl15AWEF5/cfpH4ACEiz9cDYQcgYzuzJ1Tiqqg0gLK3xgYCAMAoSyfl/RayNECTC4YFhgXGiDNx4C/jPI2RVUAYd2AmmOMHrU5bfMAoKizh5ZLHkKtt9A9XkXJ3FMYERZG+siN9AAyJI/ACnAIsNvzns8qPulVW2gkeH1wkgxzAZTzVzA7fTxBYM3qFeIYIFaDAIYJYdNTrtpoCkBulEteoyKneR4A9DVQenmUQ0BiiBwACEzszEnPyBYYgEXe/4ywgBC+wW7PePbOpULvMKEjGY1FTQZpMQDUmvkB0MpPy2ISASkxrwE2sRMCi4yUZ1C6nlsTJMOlx4QLKmfpPNnAZi8zjzcaBAx6muzwFypmv8+z2zN5AxDpACwCAHD3u7uzdk7scAc69BQ/wEY/4NeLsauWL7AZZcTHsQQ3/8uKsCxQTGqAbckM5J2E3jMTLQlIhXnSLKne5iiaBwC1YbkjiQMRQVvwE8WO95/xJB9B9Wd+8QsgkJvRfbn72fuzha5JDakXgE7f5msThy8aozAPWFkZ3QuYwYmAmLcA3LowlcOoqZEkhZtdDWSYIDVsxtDpMAkBIEZAYydBEgsAAFkCi37YAxMin+Ze7s4UGICF9H8IAsARuZy9c26H219P04RG6K0YDYFJsfsiBkCFIzzuUbEqQCVrVGxfEQcu4IoEJv1pJqk2d6O0YxzhyrGOVOWx0ujpYFNLWOgYFQKkRdfRAJbgOVID4fo/Y4GTmd592f70ndk677DREiZ+pGGG/eIAJgwApIQKqGrHP0uzYu0K8f2PTQSwiwJsx+iOGqnxEKraT4gwC0AwsxVU5RALCO+9EACqGR9pIOSLOWrwB3XaUY/iyMT3z6Don5G5WADAqOHu9GfPJ9zeXlhgRoH4w5+RamvooIWg0IjogLZjEdwgFQ4E4McqqoKtASkxrwBWrWGK4JUG3UBNs7QFCB8JpGaSaTY9KghGUQFFPslZktG0gJ6k9R2BmsIdcwgD3fb0jAXFD8Lvznj2fG7IG+ww0uD2q9WRgDOSgaDo5avVQpGbSInGxtbCRvSjdLnS0KS4lNWxDYCVm8fZwX6WnhqpCUHNdeFzwcEHxFbDiQZDRgGAqk9qluR87qDBoKeIAYyB50+zwamzp0cgIJ3/FRR/d+az768NeXs75BSFpa+WUji6AW+bMBIUJ4JMZRLsMHNnJ1IEps6Nq0MaIGb3hm9nnd5SjWXSIwWAmcL6sFDawMzXMpEooooCgOaRDnIp8ofUkEajIfQkSQSTki8NXdv5LP3y5cvIHghQkM4ioPvy5dynz6+NNPTWg+YwEOiow+fWMMUnPenvFakAEZVZ5ZAgiIP714rMWcuaNwQaYEWMHm5jKACg1yUVIjeF14HgEmEtWsFQqyQRoGoK0ktRAISRtlh0lEZN04bJpPdv3526smPone+fQXh3GdsDRvwg/8xu+FIGSN8X7NHodCTodCx9afmjbTYDgt02SH3JhUymcqUEQVjlq0Eg6YPekFj3AGBIcKeJA0DAJbE81DTSEB7Nq/9fe2/+3MZ1pgtzgShF+7rYlpdcTzKZEokTYJCYFlAg0wHcwtIgQtwpkqOwzaBD8LZbIEKAkLoRiKC4YMTFNCNrYIni0CElitQU7TiWKFtOZFmyyy7/4Mj1fTWJK7lTmSont+6fcd9zuhtorCRzr6psCGdqEkULt/Ocd33e5zWflimEts68IRAFAZcGm22GDdy/zTYxvzw/43TOD8Y59P7VN05c/fDTd98HS/DHx36PazwQFrwIAAC//73fP/bUjYsLC8uzE802h8FsUE9hABhcjskFW2sxPkB3QXWDC3jVMBQCqqvK/v43155vlGt6TR1j118vxASY7iioCXm6oTl/LkQBwOujKx0buH8fNAfH4/H4qbEpHWLZoYvvvnTmxJkzx6/e/+jTD95958a9pwAGv3/uHwED3//PpcOcgM6PDcS98fHhmza7goDiOYZzfqTZlcUIynzZjd35KCYGLN6Dt1sdrCr3+4ckQCn/dmEAdBaKAedz7rJV/gl2Nxfoqykm4O25XLdR8v47lr3eU/h4Eaen2eGeqd+dOHHs+PETcI5d/eTDjz797IMPPrj31Df/+NTq4II3bOGQzuuNnxo4hUbtCgJKfHjbZFZHyNSi8QF9za5Cfu9l77nGo5fR9nQeWLYAqN4jV//ae80dk3MF6ACvxPMK+pgOCuFTc4G2igyAl+f+RGpA68OAwTERP3Xn1Kn48NjkqQhFublLp4c+PXP8GD7HjxMcHAccfHL//sfv33vlHGsMoTjBy6k7A2gK6P5F7b/y8ZcnW3xFKEF9zQ0FB9wvgnw+poRUlfn9QxIg5/5tZw0dI6MF3OGb8dwkQP4BdrpMTYXiJ4yAK+e7m9Z6l5oX6hjT3TkVj99ZXlye9Io0JR6+0CJ9eOaY9gAOzrxx/4P//Mt1jjdysr04NTA5eSre3IHjgJZSn8AweVNTDGrVygN1NxfuDp4Ha9gzpFaCyvf+qw4MyBbwtM/nHLxYMAucyQMAvvgeSNpOF7r/xsYL1y87TS2GdSLA4LAPxLEBGJldXBw7xdF6ihvov/n+1RNZCDh24vinNy69fZjVJxQDEB9YmF2eRGP2DoevpA1osd0csWtaROYML7zxZLOpIBN6dBQKAdf34/S/nK8fkgDl1febXY6BQnyQy/HlnBjATBT2uk3mAgM2xACcv3K62ZA5awDA5ZhGd+7ET8VPTS4v3MEAYHhh7uToB8ePay3AmasfXLx0Hol0KMAj8v7jdxZnF8fiwtL0hMvhsJWoO7maRrK+CY0P6DcVEDmC7+E6/llcrN1S9gCoPa/ueLHbCgKgbXAkJw1swllge5epKa+IRt5/4+W5l7tUA7CGe8ZZmm06It25gy80PjBEAOBP8Oh87+DHJ46/lH7+Jz68ce5Pw1KKT3hUAJw6NTU/fSceS4ZjS6ML8zMum70IBlodKyOuTFTamtkUAIZMVTlq1AKgcwhzY85v3VRVxjXArCSgz2S3DxUkBJ3zmrNTerJpo83W1JSbBZLrh3dzpU2TnUOJplSAZndOR1IcxIDYqntP3fFyNOWmPWH0p4ml+8/98BiGwEvHzrz06b0/nRM4Px0N1FMxNQY8dWfkji4cMiZot8iujqw0OV2F7Y2tefCmJplNDwcCAMym1p58APSM45/LlT07qsr7/QMdSE0CTI5ZXcHtsa+g5Y6cJIDEgE358hAEALCB57TJkHWK5+j2jmkpyYcRNgFgAQAHS6ye0Vs8kXjLyjtv/Ob7J4699NKJ5//rs/PvjSBRr48GeHbpwT1iAiANBNcR0YeMRqvVaAklIwPLTnvBBlSrcwEWXWq6mSfVqgUAQP0f2mLGZbI/4c263VVljoB98QsKIdDsXKkryAjrHBpxurJCABIDGgrQa/GwYPvcFYgPMu/fUKRJg6v/ZvvdaYGmIfX33oEzcCfuffCXpYibSQQSaKRr5LPf/PIHz//wuV8+du/KFa+QotzRgF+a+8uXnwPpe2AA/5O45GZCxno4Rk+gPoymYR9oQ4shP9A0DS87tYSWRsKDbT962mySv4/sgvYlsj/hku7vqsocAQeGexQAGBxTewoPh59DWWOheCYIYgZToSn7RllrqSvHAhQ2AsAMdk6jUIB2MzSLoBAY9y49+PLzB0ucO2QMMGi5d/w/f/Pii7/55TevXxlFEYpOhQJ675G/fPmXzz8/EseZYFzg9BQTJACor7d4AhQawxthC3yyjukRn2wCMDxI/NrYTixAE04DFO+VrmZeIzQpUgkq71Mzp5D7XD7HZF3h/dEXQFsltxAMTZTCMgudYADabU2GtRFggAbQNKID1ijjp2M37t07cuTW519+/vlfb6+KvMXqEb29E4f/4ze/+f0HcwsDKAahAe+h0b0HD+D+P//yr/cOR2KxJPxuwlqvHIsREADd/wJewAwmYJ50BMjXYsBjDeTSMQB6G5XwNZPIXCRrFF8e2lbuANivBP49Bpd9pK6IUPD1YS2724SDf0gCCgKg8U1oKJ12mVtKAkD2Dj7bNGICxnpjiElyR/76+Zdffvn5Xx88uHXrBkcFjdb6yMjJhRtA9J2aiqMwzfj5KC8MPrh1+/ZfMQL+ciNG05Q+Rdcb69PHGkih+Y6CDqdjatLna1A9UxPmsqkWoLddAUBjGgfX5+TFMTVlfv+bdilx30mT3fTkniLyENe8Gm0QAymctPU2FQRAGxgAqCmYW0oigNwBtv8pD9yeMUSxS7fwu/7Lg9twbt2LuKPWek9CuNk/ujq38oyO81N6qA5AqHD7Fv4bxAbc5tx6t5sOau6/vt4TYE+ZCg2jmG0Tw4v2dGgCfh9fdTuuZ2FEZ1//0deHyMNoB73Y8j47tip33m2yzTy9twgAXh5fyIyGy7Ff51lzQQBcGgTfeRIDoBQCyP13LKAkuX8L747cI/dPbvf2rSOHkyGw6wF6dXFsbHlAJ1KMHp+UNEcQcvsB/N0v/8oyITphybp/QEAQFVpqh6mnY2O29NfS1EsMgGwBzuYB4IJO9oajZIFoGZ/qPQoPuMtkm921tZhU+OigK4dSBVlDQyEAEL3V7iYDkRFsKYYAfP93F5CI77/eGKTFwxD8Kfd/68GDI4dTPBh2ozGGVpbjkp8m16+nxbojMgCICfj8Bu8xWutzTyCpK8BFajGY7YvDM3aSlcDBgQy5bbAATcqvNXHAJVk5E/RiN5U3AA7KMwE4q3cs124ttjLsPe9suo4mA+B0a0EAvIINAHSWFSHJPBBk7t+5gGIBcn0AAO463P/n+P5v3bp9b25ISNFBKzYNsRGBBUdPrp9ixLrHj9y6pZgAAIDeY8y7f4unXgBNimyLQ76IVtfklFMtUDVBFEhuu9/U1NRyWskC0nnAFa+cHF3ZWuYA2D4oV3NPG5ocN/dvLbY08PWhqXRL2EzqJj0NBQFwnrjO3qa0lmguBJTw3wb3zwY89TIA9JHbAIAH5PqXvDqEdEIs6jHiPxAg+sPXD7cfkQ57ERpMRwFf3nB76vOP0RODkMWcVYKWP7dtecCk0kfM8ni7DABDT1YaiE3esJwcvb13d1nf/+ZtyjR8T5PBtrCzKACOnh/3qSZAnq3rgV5/HgAaXx5+hYyYyADQQCALBGafeXYMsVaPRb4xS0oCD/BXuP4jN+CKMdFjAIlWCy9KLCW/fyoVQVAnhsIPku5hI4BNwGCqEACslhSatrVoTYDy2V2mwYUO1QTIuR/EK83mtNiJBvAXlW0X8gbZMu4Fnle74j77d2q2FlUI+hNKd9PkyZp+QwEAHD032ib7E62gcK4dMNtMIwixQXJ7OAisD9f99UsIAO4N6HRKlf/OHZSkY5yod6f8FLQH3ZLCAIA/QEtgBG5BOeiwviAAonphxNaKP2H2/RtaHdODKn9ICQLwd25Ou4McSiAZijpY3hag9kqaGOV69kBRALS1jA+q5WC5B3jSXAAAnYPvHc0GQCEImJ0zXolXwjej1VhvDdf95f97MDeOhIhOvuaR5ZUBIcZxnCRJES5J0woDBBgA82NeXfzeg1t//fKWkCgIgEQK8gBzS2apTRp6jomBZdUENDX1EwD0AQDy1t6+hxRbeNl7qLzLAFvfTgPA98yBvcUA0OmcQhPKjGUJALwn15VP+5qyAvAcEADxYFCyepT4LwFOO/z4538dR1KMiXnlJs8K9Pl1OgH7gjvA+4v5BZkBEp+aXZy/o0PoBgDgnBQtGAMkRDQ6Y8sgION7XM7JEVwpJjDEnAACAMBCLrPp/FCnmgAfKGsDsGPvJWU8otluemJ7CQBMwKo1Xy4AcptBc+dkvcHWpvw0TIuDjhVEywBIhMWgx5Pa9W0kuHE2oBj66dn5SR0Czicx+ne8EUH+fQyAm3ekMIeGjnx+nqu3FrIAocjQuUmby6xCz5D+vDD7NDTrUP4HkbkjADDnIrlteDRNhijvUmD1HoUQaGu2Tzxx6PGiALA7RrwtMjNMFocBAMB8UHY7+LIyXAyFwAK0rAwEIAiIx+THG0qyiYAnVbcnFgwEEoxq6Yenxu4o0QDwhCbB7ysAGJhemLwTd/OsTnd7UAwYCxgAK40mzw6QabaWnGO22wZB71YBgNzMBlJonmjMBe81zeqosm4Gy4siGjuhwD9be7AYABo7Xc55pAgFytO1/RgAOXyAc9fbFXJR4aFQYpLxPQD5TLDINcAUFwpYGY4PQE0nSrPxgVPjMjfojlchfUwv3pw8pVKA4tgiSIn6UAzV1fGBQh7AmkQr3WPjDQSu6qdM256FYZNd/qVJ3hrU1ZwvG/V2WiiKrI4q5zLAsOzrekxm2+z+feNFLYAL6EIDMqNC1gfDAMihhHUOXlHLyqWngs0di0PEBxiDVIoj10hKwnCt3jsDcXLPwPeW3f7w/CIMDasUIMIZ4TzW+kRKF7EUjAE9LJronkBTGh5bi7rr1uCYGVi2qbwm/L23O5pb8wAwOpD+xs7XljUADsypzEizbb4kAMxQuZdlIuRCEACgIYcU+oqqtt9nKskDBRbY8OgoiwEQZdwRJgA1X/nugnoJbl5+5kgJB04tQDwAcR/8ajwuc8CAAma0WhLRAg4AQ0ka6O3smUMzzuwYRBlBGhlTpGsJABo7IV7JBYDSCZJLgmVdC95cczEDgJWdxQGACWNN3hEiFCOHTBgAhpYsWvg5QFP7OgDQ0rGIVv6EEh6LMaR3RzTJvDHqFnDQh+O+tNEfmBwBq88hL2YAjdw5hfwW3Cs0egpFgNAKCCG8Cf4yGi3UEjI7bw7MuDQWoAfclSGHGH4JvZmzRLxss0ClDgTkeCgE1lTHixaCukwNHWNo1p5uB+NCkCErf8ZMEAKAxjVcgN05gs72jLAQwtFQ4dFrPLnHqpd0hOnDSqdINAC/hgrgAMeLAjEJXsEfZVOB+mLHGBC9JK65mE1jShPETQPKmJhMBobvPL38sF0x/Bd1ZzMyJ2VdC960/4oyI93capvaVu0tCoA+mNue0Y0RH5AGQEPWTM3ltL5Md1NJADiaERieVwQ+EMQlPncmlgdiJx9GMXdSz/MsmRVRmZ9hnme4CMuy4VCQlYJKEbmQB7CgOWW4b7rD3lJgCmV0UgYAIbfjJEAGAGSEp/sVidgRR9q3XdpTzgDYUXtNGQsztdrHDpQAQDdk9pAJEsU1LBMNo8HmnO2Lbw+rDuFkKQC0+uanhzBULkqeqJtKCSm1pWe0Bnlo+USSgYDHE0qRcECuA8QjDO1n2RQdDVotYeQvbgCAQ4CuKd5bOmJ25DHRDLabwyYiH02W3sJ3rgAAvqd+Gc9vops2tTLUeLmsmwE7dskcMIiEfK6R7dXeos2gfvCUtnng22EAYIEg3A5uyPKd5ydPp/9yCQDYJ5a8pF501hsO+amUlJRDQCgNhygmJiT9kBhCPEiHJeL1gSvsjfj5pMS56ajHE0wh1mos7gGgFah8Ua8vRW40OfMQ4II8gMwJkuy/h3ypAADgBh3tlrOaOZ3J1ZeZjS7nWnC1zAfCobCvdfBQta4oAE5D88/nGh73ge5TE6aFY0JIgzYPbJtbUZ8NbhUXTQFt8zEJDyG3dS+jME0lpSTmdFmtUAUAdjhKWXk2BmAAa6BnBR0MjAlSjOZjiGNCQStu9AhRa3EDYA2ikX7VfXPsUkOeUmmra2SM/CYBQLepNWMB+kydpBEEnYT09OjL4+XMC9639xXlept85sGDJSxAG75vxzJauKsobLThlq82D7wwPHG3XSaGn3Y1lZgEXdBbhOs9R3vOdo8JQPKRkiG4fksw5NfTHAp7oJcT4XGKWB+E3/Prk0mGpzjEhqIw/RGkI4gv4QDqPX60qBqiKxzNDdrzdEps08MuPD9Exhu6MAAMcgzQ2NuEv53zaNZpSqvI9AyUczPg4F75xsEQukzD+6q9rxwtnge2NthdQ+Mu+IE2Qw2tHZM+NDvYG98b7JUVg7TdwEIu4AYPmdrAK/1nu7oGOTomuPn6IE/p3QwTQeEAvuRYjHSJrR6PJRqiaT4loVgUZn+gcMhBAFDcAUBBgRs+q3xNZyNgYCLTjtxk0LY4RAQP8IyzzFxQ6gCdZhdYph7dMEgVpYWD2q+XMzFcLf6fNIFrHKqurisKgEb8VFqdN9EC1INJ+ASJIeSB3RoqwElTvyq+2FS8CDTzftTqYZB3oberr2lAZCU9HaLcbjejjyAROEJGY8jN0UAHMlqiPLB+9XQYoXAQ/L4xCJ5ADJRwANAIQje7yFfR3j3IUXo6uZqrctxgbx5Y6FBUDkgkowKgfwZvkriiA8UJzVLs0ZqyBsDLSu0WYrPxUgAA/wi20gYmwGcztGIT0GdqyKqgjJ7vVH9qbcUBYO64KcIkH8T4aGSm6+TMksBRjN8NlB89fv/43j1BSmTrLYkQ7Xf79RQVQyiF234QFYiYRlTi/usD4nhLL7bebf0LQgrgQ3HTjpxcEOYfZOFL8GTdTRmie2PfDOCgZ2jrdxwuQ3P6G7tYzt2g7U/2pAEwu2d3dd2logA4idOlVujjYhPQAiag25Qludd+/Uqbuo6tOADMtruTfqvRmtDrWeSdmuh7r44llF8G3Hy43lNvCUajCcrNhXk33D6mAnJIYoy48Belk0gKlrp/aCcJU7346k6fnPDGGMwjDS+ZXLl6JFMD2JHB3sN2mbwoq0a1ncUAPlf3nQWfS6Mjdb6Mu0GbVQD0mVpsi3t2lAIAkAZxEm0fipttMF9vayeIyOxg7Rx8u83ka1Oay01FuoHOjhUOgnhrAiy7mxWk6xfjMQwACsd/oRAPN6aHwI8SJb9MBYS4gOONQBryRCm/gEIl798YjcVners6O092nR2SGD5ab+Td3ErOrpNW5/L4hJMAoMdlzsw69ZjAhV1A5y5MmewNpvT8+Lna8rcAMOaFAbCpFABOG4i37FiG+Vv8I+vvaSI7OPvT0fKbbeZmZdC+t6mIGFjzJBfCbp7GRp9mkqKEUrRfz9CskASLD8eP3z3EA8Qw0HoJiRD+WY3BELYRTIkSMHDLQnphrOvs2bO9vV2TiInWg6mp52NL9hzNAMfEONEKMPX2Kfkq6Qz3NUN9Y073+iuTTfaGjPrNlV3l3Ax88nXlxRoc83s37c4bDcswJXGxUG7kkXJgUxfuBmn6aBeGLrX71CCgqyAAWu3Lq2FLgHT93MTu0zwLmSAF9R9OzzPkKCMgYSkFIVxKkGiPx0qiQYqFGMGYfefpAyFiMMpTonei62zv2bNd08jtIeUi+FSRm9kKNy0288AUpjYYXC6N4gVwYnqBC3jt6CUMALMa0B59e9fmsgcALuo4bu7dsntP7nBoW1smD2xulUUXEe4IGHzdxHymV7BdGHql3aV6zr4mQ6EEYGKV8XgIbdMt3zPjFyTKHeMiUkwUw+FkMplSXD/NsjSdjLAhSzDB04AXOoZYj9YBGOsTPM+HohYIGxLgPAA6lDDa1dvbe7ZrBeeTamUoNmDPFrkCWtCkw0XIgqrihQN0AgzN/RcQUFreHAEAyDpYpDBczqMh22UAnAalbsfK3s3q8iANE6gnPSvT1ayILg6iCSilmm1nzemWmkwHazzb7GpTOgeFAOBYiZEWrhE8swwACjo/osQlKVrkOC4SiUgRfODXLKcTRYFLulMACQyIMMrhf1ij4DDAWVAQLPiJ76DDuok+cAB9y5AspJNFT0Ia68gSrQRq6KDdnit50t3s6L+OIC1+b8RszywYb7y0d0fZWwAQCPM5FrYCRTRXIqazuy2jp6c0UxbRCFbiaZX5YU09GgCYzIrudEGJBsekPMljtTAqADgUifihCeypT0AIiA9NuZNijI1ICAnwf3AEAZjhLBKigfQIOB4p8mA/Ek7ReuVjQSIRGenqPdvbPQ9YsWq6A4ywkKVy5euYGmiy5axAaz9ruHsRYQv49ghej5FWEH6lnNuBShB4EgNgeivsEM4ViWqHnVCNac6IYgJG0eJdVfcpXTG5PH7pqM2ksCv7C8lB2JtXQ1b57frJjflxiYeNysbaio8nfYAtGnEneCoVjsVYThKEiFIBBg2IQEIfBVMS0vuhdxSjab1qToQ/9Z3tOnklJ1m0GsO6Re3AeGvHwlAWVwBTXLpNkOISdsS1QVASaTUpbY7Gy3uqy7gQlAZAq2NqK0wJ5MnEdfU1tqusMJMquOUdylTY1ZdyYfy9o73Nim5YT4FVMYaOmywZ5U57ANovCPr6QOGmnpGPxQLygcsORlX77/FgRnCkvp7CwQLFooheQQATmezr6jt7EXE5H9Nq4QZ92mJAx8rQRJaEgPlsV4tjEQ2SL/7KoMuuWTB84fEy7gcrlcCTZh8BwJZdeQDoc6WXg/c2qw9oBRiX6QBacfsvD10Dgq3SHTqdDwADsPGS8jCwhSIAoCgJ8QFP0baePgKVARAKUSyDwvdJsAg99Z06lidWhILYUEhRsgE4PHPy7MoQEq2BXDQxhzUCF/gbGJ/JAkBrU7NtQqcsT7syguVQ0nngy+UMgIMqACAIHIN8d9fF3M1/3c39r7ervzQraZRjGBpmiiSjGgR0Dp/DYYK8j+e0y5wfAs6s8vIwiOwBGJpDyRKFfWs0LCVTSSZR70n3/2GEBHk//eT5Xz6lY+WZcQgfkaTHySPDXrw0NYTYkCcPU55oZN6W5QJyAIBFxMbRJXVLKrYAZjW46XmyjKcD98nDgCdxqxcDoPZiex4A+i68rNYC1ewexoSGVSeQVt6+Po35dU19RbpB8Ow4OTSDZg9xADEU85Rs7PCCwHIRLkZB98go37+I3rkP0uH/+NwNFKYVBLAoTOmZFKsbWOXCIWsByQCjlV3JAsD0UNYKkRaD0zWI3stoouE8Vy0EvF7OANgtK0KcbFIBMJcr/nuyueOVa+lSkPLz8nVMAzeoVSMdj5tBk239AADCpGjPKwUaYLQoLOuBWGg/KfUgzliysOtJSoiL8mHAgEhKwDBDiH732mtvnXjrzIu/f1znVmxACrEEAGE+YfQUJgt5wmM27XaK0YFWe5aCGNBU0wnQuT3LsOzGoDaEXx8sY0rQjl2XVADYJgEA+/NWxpw02S6fV0xAV7Mq8GG3YSdg1qquNp4b7IHxElk8oDGvFGhwzkZCAdkD6AEAjBsJwYClVGPPDQqyOEiI6sMxDnsPD4PeffW1Lz7+5K23fvqbx+oisnIE0Egk8l/GQFGqmIdfbc6UAlyOkUGbS2v/HSNorDO9K6T2O/hP1XGxzutlTAnaVPueAgAfAcDOgdzlGafNhq5zl1QwmNNPZgKNm2VRVtVWvj3Udxq0NuSfW18uAFzOMdajeABS7V+D2VMf4KVwKIbYBG4D8UlIIKxR3Tv/9sWrf0YfnHjrrRNPeRFLq+lfElpITLCUOWGn09ywFpthSCsjBXCehCECtfbfPrrtCegGtaqVoLa5MqYEbdr/tgoA+wjUvGvGX84bCTJ1XLmmDghmrtW5gkYc5FEpK+SOvjL+ZmerSakNn2zKE2seUvn/2APgEqDHWCoAqGdjdDSMUBIQEHLTLG/18N5/ePWLL7743cf/8q+/eNe7pFPDAH0khr1BsMTH8zDj6R1WLY4Jr2YRosHuw/dvU6HfObJ956It/W0dbRstYwBsqbkiA6AFA2BL1bZ4LgBgdM557ZyaCJo0m7hGEfDuzZktoq8PnGtzmVrTo6M5LdgVpZIL6R0GgFSS2gkGgIH2EDDAkSAmAkZKH8NqMDHpz//2xRe/eO21L95B9wbvxVGKJiaA5dYCAOBprENpCgKvyZteggOiEa0jaNRmb1BdQP/wwe2wZCw9+dp+sZw5YdvOZwFgez4tuKvZOXGx52heid/WMIxu3iU/U8Vbjs51AkLk0mBnjlqryzcYDiikLSjg0EmUKsnssVpFkQ5ZYhEWCRwfSPhZvcdiDQrv/9ur//bqF7+T0OjtpVu3kcSQBFCU3Iy/JAAgChAWlaYgSOKmC1mGho7WQTRmt5vS/OaugYO7J12+NOO5vAGw/WI2ANClfCqY3TR6WeWGa67V2RwHUVYyLCjPWV8ZP4nHbMg+nhxOEDSbFTUPmAaDJJCOSJaSBgB6eEk6GmUlvySxUpiPcXgS1MPr/uHPf/jdbxFaunVv8BaIxrM4EGRSgkivAQCYGBtwyU1Bu3lIXYRoaO0wDQDBwa6hNp0dOlj1NIwPqlyX9vPlDIB9c3j9FxhsDIBNVQd1eVKhJ5ug+C+nyI3tWqZPS8eMFy12YHfQelKW03mvH6ZCSSedkIi1HdgRRRCClAEptywRWuLBRtkkHQxFhEQMiW5JkEgNyRhI1dXpkO7w6pHbS6AV9uA6EjEA/BJH60sDAEbGJGVg3DHvVfbgGXwdE+Mge2AHlmOXWgJrih+sqp0HSVF1pez5cmaF7n76dVn0EQAAfe/qPXly4f0Gc8fU+XbVHGhedUPHrE636Gwwt/rI8wFdlX6w/IQxCvSaVi0TcFEKyeN8xiAYa+CCa9V9jMbCAAilIlyIFyIhS5JXK0EsWsLn9r1VUIy8dWsYB4LAJxfAF5QGAMyMyS2hVtvokLw9BqrTyzo0fxeXLTPLRGdgFqxmGmbKlM7m0XPlDIAdpBQIhBAZALv3XstXBmjqWJ7rUWe+tHq/rR2LXjTvBNEleTzgiq7X2aTsFDupmRBv8dlGWJWfkXD7GUriMv16PACSIfekO3gxVp+MhJMpKxthPQHVXgSiQhwD4MjSIL7/W9d1EkPhllCSXgMAID/PDTe48GjYuCx6CenfNBqf7cDhSmaTYOMEdP+2j9hb06PPZQ2ATbWvaAGQ3w/GdDHHzODlQnxvQ6tzNo5WnDYDefSYUHnX3EAYo1A/aNJQwecFXvEAVh57ACGpkruseASMoUPyiVrqFRR4osATEo0pMeDmuHCmZRDg0TgA4IZsAK7fO6LjaIYSUYxmoqUtgNGox3RGs2PBOwEhIDx/8yRQ052kb5UhAHXOQ/+/+vFme4sqHVbWANi88z0FALZJAMCmrXnNgKO9Jpdt5G3NeIgmDPA5J4bQlMOprGMeHe/F0bUB+igyhVBxAPaBmFKkIzEgFUZ0IM3rwux/9egp4HhBAxDTgC2hEFQDRU+CEyOZmpExENatLi0dniP3Lxx5MKoTaSqFIhSzhguAiSM2PmHzuYZGbPj5d+Av3QaCkr7WBg0FsP8mCAJs2rroSCtHlTUAqrZdIQ8bOIGjmPtWm786tA9LQ5xv1EwHZLF8WkbQSHOHr7mvHW+XkjdzYS3+XjUIMPjuTiNV0RG4ICQEUOjd1oTIJWmFCEo4IpgSGAKXQHrAxgDDWjxiTOQyDA9rPadbWpVANfzedSwZ+mBQBx9BEvRUdC0AMG5p0gG97EWb2eCASpZ3uQPIfw12X4uiFYB/FHcX8Mr4WliRovJdyxsA288rvRvHGAbAzutt+WmA2bl8XQkCYIy4NaePChKS8fkOoNSePto+OE64drg01J1uHIAuaJrOSzqBlCgobQHo+UtSjNIggIBAT/OyNwjwXDTAc3pOzCQNkNF7pcOrc3M3VlexuPSRxzncEYSscQ0ARP2MiCbuxkFGtrXDPIKGZ7D79zUs4pxP6f439tum8c+hZtLhU6eeyhsA1VjZDSx7g2MKkx9rhvI2acOst900fClNDW3N2/s2H0ejLU68f/MSWnG0yqWhTp8pzcGV1HkumQxEpSTZBRj5FJdMSWw2AGSOJ/R5+SBMiYEYCMO7JcaTCRrD6PDS4aXbR5ZuP7gF/3ELlgskIQgo7QLwp4Z0YWRKN+uAL3kczL+zBfaL2ZumQPjG1KoUAk/axzANfDu0C1XCc3kDYMfTL2sBcCBfIwQCv1bHyLl0eziX6gGbP5onUfymywFudC5usis8e6V36ANd+FAgLQGD6aAw9kE6w1AVTkGnNyWJuQhQQRCyQH8XmsahWEQjCxxidTgRmDsC24WWVqEexKUogVsDAGQWgU7W1U13OA1jaByMlpwKNE9NaAaBTraO4FGwfXUTDrU2dK6s90bJaQD4ecc0Zr8eyh8PheVALY5pda887vfkdnpbHY75ITS87DoN2lxERgiP3veQPMDgmM04AFCGlYlcXAyngfAm/RyQOmMRt77g8buZEI4JrRaaYyEsTH8QyQsIwHHg6OEjtx5c9Na5WeQvXQkkzoeh0IDzLjz/MXXFqME2QwBwtk2tdg9iAOzYO9/RpPxeeQOgiqQBkLU7FjD7dV9dfikQGib2xaHLKk24ueD654ZpLxq4eRLoNJgnYIAxUdI6MttbhyOZtS4KHRQEAPC4J2QEDMvSjJtN6fXFIKCnIBzwWPRSOhc04inR8SXpxoNb9w7fuPXg1tL1gUgYheXlQUU4AWB7/Ho3LQ7Aqnp4/g5bWjJoYgwAoE60gFrWMJ4G3rQLFguoRIfyBsCBc8q+kGWshrR7T14lCI/Q+0wDV47mkgKyN0A7m6fH0anls48PO7CKDGSC/U0gOwHachnjTcqAZPcPXhaAAUCJET2jL3kgS6QS1mBSoBRDAowSOoZWDy8dube6BG7g+vUjR4YiAhs0WmB5qNVjLUQzAQPgd/OhPd9eHNBNNTjSpFVQkCcAUCrBnTA3iO97y36YICd7EYAgUtZ64dANaCddf9ti3UEsH3+ugDIARHKTc7ns8LydXB2um0Movlc3hTU54CcKJsB3d0Ur6aV4AIgCyYgoMANAKFSk9Wsdt5u3BEUhEVA3zGAxmdWlVXL/gwO3j9y+h1AEFKQ8iSQbtgYKGQB9Sh+qZ/fW1qHpu5r1wgCAyQlbuhJ80j7rJQSgGmCGGuTJ2fPlvTyURIHQyLfN1mHuW+3FfJnYhqYG283xvrRkZOFd4BAK3HUtjwzpwAk0+JqaTgPXFCRBYxrirzoQwDAcHQAhCHeKoWPKIPgaVoAJ8RxnwfoRIBSg91N+IQ4AuPfg9pIw9+DekVurCPEWJvzOU4/d4BIFRGSgrBQMpHR1HC8OtrqyqGqTsFtALQR2+eZ1hAJ4YLzZIdeC2y+W98qIKhwFAgBAIQQjvUAhAOtp2yfi19bUgDMbbE5H8/ICiLL48Lhde9+sl9Moeih0UOwD2DCo//D6cJKmWc5NaeaCcw7DqEPDNM3FYBAUJkHxSAAlIigI3jsyJEEeCPnAPQmJ+s/+13899+IPvhlJFZARgaJCFEl+SyCBVjSzogbn/GQzSAL1KxmP62YdIQEfBM6IrH/T9mx5L42p2naNAAAkVLHz25ZfCMBDYS7X8Fy6OWAqLgHW6rI5HLgl2Ipra32ngPipuQMlBCBRIK1PiiI0emIxQcKDwSm/ctkUOeTywfYnlQMzopQeUgZK7/fLYwU4F1xdXb1x+8E9wMCtG+j9L1577d9f+/c3fvnYKltolQhoT+KJMg87oDUBzmWYBjcprd8es31angSrrlt2KhT3Z8p7bdTmgzAMApfq813HBY/t8XylOKjrw+igt/tofk+4oBKo/F/Nfa/PoUQga5eLev88J3F4+lOSR4Hlw8bEcIphUmIsJqYAApSbDSdZfJS/EGORiBEgmwa35AUAQBZwe3UJEsLbh9Gn//PVV1/9xbHnvvv7G1zIWohobiWVRHQzoxkCXLVJg13NAmF/7pg8DLx774ICgNefqa4q8yCgBw//+1xzcgWkgEgI+ADHLLqmSQvWcQz2sXTPJ9MIIiNBejzdF3NDwTeEI0B40/DUw2KMhQO4wLAAOoCbYxU3AKvD8B9znIBYmlcgALkgdgIQBRy+hztDS17pz6+99q9vvXXm+e+9sRQLlFKSs2U0Q5wLkz67Km7U1ewb2bpZ7pOPOQykFtzz9I6q8g8CiFh0LamAXMuXiIPyn90+oKyYJHXDddy/q+kUl0X1sEaVxxtDSAwZydQniH/guXAaLD9N0xQVRoIIRQF3mGVFTm0S0FANggMCUomwIOHukV8ZLQIEjK4evgEe4Na9pSXdO28dPwaM8eeff2EpWZxz7qEyK/CgUDk96XKpauG2pqYBWRJoS+2IXea5vPx0me8OrTpwRa4ErQApsNB8KHaNTXgYqFeNCZrWZQBm4rFsAMgjYQyLYomAQvEwWokAjJoH0CxKBKIYDbwoiaRNSLrEsFqMtAcDgWiKlbCcjCwsJiwdPryKI4BbRwbvLaFPz7wF59j3v/cfUgk1KatRGnSqYyI+59io3ac0/k6bW2fGFVW4/cM+F3EMl8tZIkbuBz3bhvkbtmXi/fbPHS0UBLTAPOBCo0Y1dE0A2GZ1ohYAqgfQC8DwUX8fHjW8Z1kWBl+pFIHxb/zXYnKb2I9DQ4piGIonlV5c6QnFJNaNjQOVighyLeDBkaUjt+O//ZcTGABvvfDdb0YsxlIzR8BlbFFrmJNjDnUNenczlAGUut/OoWY7AcCl2jK//6rNT1wghYBFEv9uG8iaDlN9I8z2DQ+fTHeEDGsDABruyXotAJSpcEaKedJpAY0LfWrvx633C6zVAk0bRoxgyr9fzyfA9luIDFBUHfT0WHlOYJV9slAOuod7gvceHEEfy/f/1vd/cIP1lOSHCiNOZTbQbh8BgphM/oFIyDavU8o+NfEJGQBv7ix3AFTVvI35G/YZIox+qNDWEKj/wkgtmpHh0NiWWw30ufIR0TGJ9FoApKXBIpz628ZoKJGIYgcfxYQwPkQjzoOHx5kwp8eCgSEsEKzogGVF80nEySMhAnSF7t0bBCuw+ulxuPyX4P+f/+HhklMHwCryTjjVsaDBBacieQwdbMdNdT3ANu+sjQDgWk3ZA2Dfs2RzaNPwAZIAXyq0M8oEvA40pZqAvmwTYGg96zPkSoK2jkuhrBAg4VdagVLQmp7bthrJHROBGPhPVqoPktFRLozFf4LWIgTPQEzAfy0p/s67elgC+39Pvn8SA3z3MYEvPXYUhaalT3ZUpuEVpzzNBPUOg9wUJ0wZIA4RAJw7UPYA2AQ+oB9WhoySPtjWKwVXBkG7Z3BIUQOBCZFsE2AGBRZDriAMxHpaD6DEgJgQlijyPq0BPaKshDLAsjSlZ4p2eD0xPBFMxX736ruQCtxYQsLHJ1QAnPjlU8Ia5JBAzCuLBLXgbcJOhQ3QZcIjskrIdwgAQH774r6yB0DVtrfB/DU5FvA3v3n/xUJKwWYzyDyj5f6jOZPiaSeQyxRrHUJ+LUkrrQyEZ74LA8DqoWJCxJJI4eHRiBhL8sai2gEcx4CTYP/86hfvS4elyLsfnlEdwFs/+A9drKQBABOQAIIw/opbnLND806yAwczX2CCTVUGPojmCQDantld/gCofhZU0kygFYrN37bBjEhEW2OGFgTkqfhknyoZ1lQ4FZS144BoBW1gMYulB40A/LL9UO1BBZd+GTETPAXcLguNtcOhMMiJFk+xMC7CUnpafOcLGBf/4NOP7r+lxH9w3nhMFwmuAQBjgD0la0g78TJhU6+yNMHePK42/w/q5m14r9zLT2wqfwBsqb0Ab9oxM46t3UFNFNjZqekJw74tb3NnjlpIQxFR4GXEZRO15TIQlWTd/oKT4VYPNHugDCziAJ/RU9AmgGpQorCIkCeI5eEo7g94WPgt0IwhVw/v/6UTJ76JIiUlxUkQEaDRCl4cBJXgoRmHTP+EbYe22fS2+H11ywQAl2vL//5BNfwK6ORBQIzjnd17MlFgZ78qEXYaFq7jpVHpMLDkbsiOWa/gBnmnvDIQAMBPCRFj7rVaPCAIIwI5LGaEgUCOZcUwFgdiJX1BGbFASJBiKXfdp69iABxPv/63Thz/ex1rXOv+cVFCGsYLRKEQOASrrLtlarTBuZwWBcQAsHc+ElkgLgA/09lmbnVO7STm4JymCNyjWRnja4D1wT1qQ6B4OdDsnBlHSSphzPMA4NtZLA+ayPUBIP8kJHkxwvEej5EOh/VB8PIg+SQKYY+n0F6ICIsirPRb0At49V+PK+7/JXAEN3SiZ+37h0+YgqE2oDo5xgZcdlIGOA1NzI7ptDBwdd08AcCVA48CAKpq3jxqa3Iu4z25m2vm2jRSsaoJwCMhDlgffLetlCC4rB/TPITEUDCLnQc8LpIDiixDhVFuEICHPlPAEY8krUbI8XCbIOAWgCzKJ4VYnrkA3TiJ40EkNMVhBPziNUj9yPs/8y/v4K1TlnUAAG8Xc/oMsD1k0OkjWWC3yQDKAfuVJGAzBgBOAx+FJICEgY0gljzzODaAh7Q6Md1q3A9pQoN9xjti7y4wJ5pdAmwaQEmLMZudqWjDUWJMz0cFLlsfDvJ/uH89F2GVPj7mjEcjLMtRdEpg8xAAQTxLMfhPI+//+d9e/cUv/v2tt44fP3Hmk1Ukru/+AUQipq8CZ3Wyw0Dy/V6Twe4aThNAFQB0PrPjkQDA5v2X2w0wA7g9Jwg4erqrLUMH9oEuzISjp+RWEIMTJDdS+XJtchJIxVjaEohl+wArXEaSZ9gIp9dskTWGhbAUo+kkiuUYdZAJQJhGIMFIsPQ+WIBf/OIX9+/f/+izOiTy9es8ngQC0Rh789BUR1OvvOkMtCzjafbPvrpFG2iEXKjd9EgAoGrfeVD26pgmEU+tlhjapW0AOGfRmFNhUHcXzgOw5GIqTwDSGKTkMmAsFrQGeKRd/WAEfyzSdEziRKtVmzZAHiAkaTqMwtm5AAx6CykRAkU9HjQGItAXf3jntxFBpwNErDUjrPmsbLzZaZuIL3QQ4gdUuw0dixllcAIAWB5Q82jcP4xDvtzW5FzEMdDmbdc1cpEn1ZkZ3AAAfUCv2dadrxeT8f+ukUICsEoIABU+mAmyWjlBk6lBSsbSfEzguGwil5UXkpAGYDng7F2B4AHgU1h4Uld0C799R0BDcYRibqAO+hPrBAD+tDAkhNVCsCQkNENA8WIh89731c3aoDxw5dAjAoCqg+eOOly+gX3ke9fQwtpdvWmtYPgRLYMegDIwkTcmiOdA7AXvX2WDMXoOs8S1JsASCAoSzbOIjdDZwpGwBliiJI7Co59aTUEjeIwokIqjpLcAxCFBmOp7L441IoBKElovACCSjJvvrkBbCEuBYD0Dl3Mu894Pjk/Ygdf67O5HBQBVtZf7m51T+CeweZe2HdA9053pCNkcQ0MumSqVXiCQdf+T2F7nh2FGWdyb8XOYJWYNsEh97R4rh0A0CkEVMJBf7mH9iKUoWtJKykAAzwJ8jPWK6Lg+ya6c7J8jckF697qDgHpoPKzcnfKaSMUX2lsGW8tAhgB8CPgAXUcv7N/yyABg3/k2l20Rs4Kqtmm54Z2wSyctF457PPM2mUQNW5ZytsLZbGNF0jCFEM64WfyWLdCOU1aAQ2sWhVOCEObYUG4HxxigUDiMWD3vRlx9ZgdMkkybpgkmYZFb6O1/00vUIv20Zd0moF4YAL0guw9iPdgZCOHrxN7Mez80bIYV4m9uq3p0zs5XgBj2JE4Eq/doR0S7ZhSbj02A3T4+bFNItN3Zk8JmO0gFZMVxeYRwJsXJk8KBpLICDrwBF0ORJAf9fX9uCR+6vigVRlKSD2d8hhVnkUZNdymJAdB39CKZMvevJRSiNQFJdHNw0GmGsTBszVo7rtRm2F/bB32+k0fP73uEAFD9bL/dPkUgX3s+ayyAjE42kvqvoWMBLTvlSarsSWGDCygjRQTg0zNhSU5OAK1GDqy6BV5hBEmIDUfwjGiMzk0eoemD9CkBsVQmDAiISjdR5RgSAHQdfZNQRPz66LoB4IkKu7aOOXEhGI+8upxaScgDk3ZX5+tPbHqEAABd4W7XIumHHxrvyWIEkmJ5IyaEG1wt42A1m07nNYV94B3YwmXY9GMFACjOHNJwDqq+YM6RFBYFEfSA9TExTz3SkxAQHgQUREmy4BjREgghRXHMKkeBTErkFrrOtl1g3RsEABSD6upWnODQ8FZMg801qJkBqply2I6+UvMo3T8QQ852GEb2EVbItaylEUQzr5FsDDcD12+6Qx6n7tFIgcGQnReu1FJ4Nptyq5VgtcMLWZgY8NAIsWFO8MNYp18vsqE8yUBPVEL+UIpDSLYuVmsEKSrD6kf1hwEALSd73k8REnFiAwBIIDTvaOk8SliujgnthsCdC46uR8sD4DjwImgokVrQgeFOrWR8k8KZ6TEbDK6GcV2zg0xTQoeoJbMT7JRgKbIAID0RQLHpTjAu54UTKY7jBGj6GvHIL7SK859vABCQDEX1EYTHTHDM6A5k88z1SW4U9oW+fiPMbCgPxD1IES3ae2VRQ9CQ1jb+apcd3S8/sfnRAkBVzc2OZjIHsWPre9mTQfKTJ4Rwx000dldWTwABKTUAsI1klGDyABBSJUA0uZ4V7pKLRSSW95CkDjiAbDhUn4+AIIcFgOpTHA2yYTRi02EG1v0i0+PsMOyL7blI0gA3v34AwLggkAO7u8l34XLNaebAN+2adfY/UjmA4gQm7k6RH8O2wbas6VCzHPfh+i+0TNCMk8xSZBoCEAAUZeKmEzZIAjQVHY8njCLJkFHZIwGFPVbkCyRxgfoY4txBnAVADVBbQ1SBFVud6Tp78hy7YQDAmJjX1Cezm+wzj2v6Pju2znScfHbHowaAqn3P2CYIBWbH3je1aqHNrfIE9Wn8WJzzCOS2iZSu0hAwO5rinNFaVKBNHegMc9pMz2qMBtM7nmCRCO4UFUriMAsciQnYLwpJgdbMKFEAk4wsw8bIa2RrgJuu35gJmL4rFzQdy1ruz+4nm+6+WVP16J3tU3fHCClm+0Bn9lwA2QsMBZNWqPcMw2gNkYWHHlorUd3WKsHk7exSPQC0grLJYJq0z5ggbJGCWbzRw0uIYyxGFlGBHO0/2bOMAQDe4/wQBLg3UAjAfSU23iTPCtvGNAZ/c/WIrePi7kcQAFU13/n2NlwN3LLrmnZvCIRJxAngjQA+qAUM2+3kf3fjhgDMDOjEQFEKr4VWQwC2+MAGRIHgIZJFbi9QLyKUjKFk7meReUax4bNdvabVJN4j7Y9a1w8AXIiaxiryMMv6ZHX2Q/jTzkfx/qEXeKCmWu6GaEaEQBak1YwlU04bTLhm6tUtOMjKSOCJtOKNUCgYqF/LAwAjPGQtDgDgebNisUKuJ8BHEMqrM8uL6JlUZLGv9+ygKLeDNgIAaAmdwoukYSd2rTbk37bc8cwjaQAgENxWK9PgdmrXiILmWyvJBSEjMNh8w3tAd5lohOMeoW0mXtwAqGQgOQQo/regXUBjtkAxGAWCKXdemKFSDSER7OodJWvEIAo0bsQEuGV1U8d0Fvlv58xiTdUjenbs3Lo7Lw7swdEfpk/3m3DfdGzX3hEbKEQ34qgAVs/n0zw1OQCvhgCsWOJxwlXSIlvCfmP16PzfTJAd0snIRF/XdGTjUSBuOQ402A0ul/bBb9607dv7dzyqAIBlcjWkJLZdoxdEuv9moM9iiSDoCW6t0a10kKnanpYG1yDnKfW01RAgoi9F2OXdsnzcho4CL5qb6+2bJ4JjbsqyEQBgdsGyAxYh1mobvztqavZVPbpn04FqxQloUkEf1IBMIKrdDdwpx0T84E7vTIcJayn2GibiJZZAZsqAyUjCWhIAWD5ugwDAHx7rT7ulP3VPHMaLBIuOlBbPBAftrc6FLA9QvfVA1SN95Newaevb2i3CeLWOr7MN1ke67IM1m/bCAmlcIOzyraBwsOge+HQIQLNsSe8MnBGaTVqNGzQBVtkEsJN9Zwc4itloGgASxCxadGZ5gKrNB3ZuqqocTAzQkMN6m6Hw3+zqtuFa0NRWYI5NOX2wa6ffPhkROVyoKVIGVKpAjOQu+bzh79FirN64URNACsKMH4pBk0ISPpc7sSEA4FUyk3cn9meZwO07Kpcvp8PDmRkBeSbc1ORqxXsAdQerDoDeWhP00hwDyUBI5MLRQoGASgfV02EpURIAYAGYJLux55sxMZS4enYFiWH3RgEA/z4St93MIn9u3lK5erUuNNqmaQubybo40vvDYkr747NOENjq84I+byAhckwhI8Dr1SXfrNW6hgWAmp4i7aBdIbbWBcp0I4ab/hNiwxsHgDUa1k09qkn/2sHA/vOalkCaAdQKS8a2QMtswGRrPv36kMCJPC7VhLGUb1atBtd35BDQL/hLrguvJwqwHNT6IN+DSMAStGzIBDDQFR5nw6mNVYKsoDxm5bm92ypvvlhGsCszJtKvdn/BBywQdXlcDXAdPSeGJQHGeoLQ2g2GrFpWiDWUVoeVoqUjfGwBeKgEGD2WaDREU/r1Enxh6kSePBYQhwGwgUqQtT5BuZMiW1cxAMXrQpqmQHprXIt9VreNRIljTvPJSyxPhzkkMTDzEWQ1k1xY2VsVCI/EAqXvhXYzQBkTUilMEkj6U3R0vQGhkmnSSYR3jwAzfL0AsNIsx8ZEVtIdqlx0cQRkkkG5+Uf09czDu0jHoG7aaeu5yPE8neIQm/B4ohwbVLdEQotPrQImBT5Q0qaDMAjDIkHiuIgkAQD06w8HjUpLgOckhtGvrxRIrIQxpE/UBwKhsLCzcs+lbMDbmb6wqrBpm5Ln6LbrVoBTM8xhqddkREhi/gbU84DjY7QkaL/iALDih9FT2qnT0A1k3SxiaYahQR52Q7k8IxcDhBS1noYwBCogPUt6TIEExfM8u2tz5Z6LNwh3p71Au7oPstWmbFaoOqC76ei/MC65aYpiYgIbDXiSHB3wWBKUP33/jFsIB+rXsAB4cygtCkKS5i31G+roKLRjWkRhLCW9VinQGsCbReBzeDwgO8pxnF/cWkn8S9sAdVjstEkJA2CwWmmXbfMuO9v6hlCMohkqFZFCgQANFC7e7c/sfaBEIeGxWNYMAqFjGBGEDXjxrJ4QHiVfBwA8nlREpELw/BOgSpZk9Cwn7qmu3HLp/tD5tmyNSJdtbNeWNAI62romkSBixe+ILuXxuAVCz8ks/oiwHmNJAAC9yy8nczHEUtGNFoRJOZDCk4RrAgB8VIQPgAaJMYmEMJYjpGKRugoA1nADNaNya7Bd0QdrdS6nf2jbvDfv9t9dHEZSmOKTjz/J6fmkFNOsAmJSAuWpL5nYq+WcZASCyVRoo9VA0hFg9AKHWWEl4UOi1AAUAPgIYimysYpiKgBYEwCbtz95WSsR2OJoHk/TqA/U3bzb12G/OQBWIPnbx56Swrw7Kc+DqyFg0GoJFg7Fs0WEklJK/zeYAEWFLiLh9RIlZkOAXsiJRhhJCsaQlKQVkNJiBQBrn31Pv6dViLODolKmZwAIsNmdTSsDSFd3D+TaRfKzTaUYSu4DiaD+FMx/9VGNF5dtRRLAE4ukQhtuCWATQHOCm8GzIcaihT8KVkpBqZGWEMukF9bR4t5KELj22b3/HJaS77SRONDXsaIZpdpetwBLGOwO08qwDv3HGx/9lmUobM9ZN83o6bAQ8hiD+QDgaU09169YgDDtZpN0/QYRQOZPaQ6lmBKVIKMxFaFh74RFBO+fWVrO0Oz+yvWu5xx45rI8KiZTgeMa1szBPdN4ZajLYZ+P//alN+6/HwH/CjG9FMM7ovB4fzRv/j/IaAAgE8eYFACA1qfcGzUBJIbAa+RxJagYJ8gaYkMQ/YXA++s1IQrlFrZXLnddgUD1rmttZIcQtITtjhHt6FT11jGbA88Odi+iD868cfV9IclTFCNKkaQepTzG+mjutVijGT2P9EaplCTyliiVSm24LwwIomMIBgRLaAQEQZLU40ZIpLU5Cs1urfA/1tsd3PbsK/I4PYyDrWTz6GpHWp1YOt45iT46c+aTJcT5aYr2s5DZA1UgmMgDQMJN5xLHIAYQQ0aPJcRvOBMEHwAAEPFsQKF/aySqEtCpYpHg5rXLKalwpRWwAStQvf9cz1EnjAUDMzA7dNq0c3jG6YJdEaAU+dGJN459uiqwSYbmU3igEwCQH7lrAJBQFQQEMYE3SGyUGER8AC0iAoACE8KeIA02xRhISN5VMfv+k0JN5V43gIBN259483SHz2cGZmDOVtVtA4sQCpptE8Po3Y/u3//m8NBh2A+YRCAAaUzkJWdw6elJTnV8jAEAkDrOxgEAPkABQL5SFLQlWZg5sAYYNPzYO2GN/WfopLCzQgbYIARqnn3P57Q1OKdzn86hvSsOCARsLSvDQ8PXp+YbZqeGVwUiCRbKc+pw6SooMgPEAABtAmDZAALSAMhTisJa9GELZH8iuvfcHyPa66dEXU3l/jfeHNj57LzP2TGb10TbvWvM1dFgdjlsTc0+m8MH66QnToE0sNHC5xVojRkAKO08DACURQzeCAASbkoBQLZODNaih5EEiCw49M3v3UeZGiUNPey9lQTgbzECVdU1zyz7OvJ0FDdvqRmc6XCZDT67He8RMsDCQS8Ighmj+aP/Ggug6P0QALi1zDHL+hEAtUQqjEQiFpcVBASMMSxOFogKh+//4BNJYjLXz9btrFSA/ua60LZnbo5tz08VDz2+7LA3tBgMKnEAgS6QMZEv3JABgGaCPJw1AW4MWjYSBTIqAGit2mACF/+NgRC6cez5+4eRW15NTkPfqW7nPviCK1f5NxYFqnYcqt1f4Me3u3aqyeZTV3OaB4iuZyh/k5sGAGoIAH3jbFnY/PJhiX4inQZApiFotTKwhQDEJZjIN88c+1TAirIMQzFhNrK3ptIA+L+OBbZvKVgseGYWVnPKLYNZHWED86FCAFBMddoDYOJAlgZINPq3ACDDDDYGwxztgep/UvfUh58dRhE/T1H6ZCwi1G2vFH8emnHYsm/Xgt1Jlkk6prHOL1xOtBAA+GwdQYU5oi0VJDYEAFEFAC1PkluDMbyEwmMUBS7GcaKfB2FRNqLT6by6bZWLepgQqJmcgUTR0Gof5CCqB+Z2sFAMQCtXx6QBENPqQsO/C1k2BgA5x/eDbbGSvSOUxQrqdWwkRdM8k0qygg7B7XvjcW/dwco9PcQsoWrfEys+Z4MLlsh7SIRuKQQAeZRbVZItAAALv27JHy0AAAE01H2MFqxBBvMKHM37RY47XKdDXvkAAvZWfMBDPZu2PbNigwUDSStO+RljIflAuW2T8QBAHZGyAUCvHwD1PAAgqSZ5bj2fsGAr4EmwMYj5uOGp2YU6XVwFwJBXV+GCP+xEcf80JIE0VnjlKWshAJC2jUIHVAGQtfbRSK9f+tUaYkQhmenyJ5NUCMp/CZYV2cHpRfPd5qcPbPWqCBgY0FV6QA/dExx4ZiiCI30jXUD5AQBA0oD0/CDZKZMNACu/fulXawJo6SoAGDocEUEuJMrHOGl0osHmuDvz5EEYZPGqZ2QAVcjgD/3sq+PII6d4a3EARDXFeTaSJRlu5Wnj+qNAPg0Amo4JMZlhxKXEOYettaP5SfzgD6hBgG58bBxViEAPHwAxnP8ZqURhF4DbNpoYMB8AodRGmKEEAHiwKMUJWDSOdnMxivZzsw64fznq36lTEIAGp7y6SiPgIZ8DKJkgFiBaMAjU4yjQGMoCQJYWnDWaXL/ypxHIPmHcDg6z8O7x/ScjmPtDs9N3J55W+GtbatMIGBtDeypjwQ/37JRSpNhTKJsjDQBsH/gMACiOy5aRsCQ3wA70RKUYLUYiES6MGYmUSO4fSL/XF59I3/SOrSoA4isjqLbSC36opzYiV/sK1XOwsAMe5tckAQQA2XLCKWr9wmFWixShkmI4xdAUw6TYSJJw/xh9ZL/mpVfvSTuB5QFUKQg+1FrAVk7WeYha6gtaAGjbeJSNUpkZsqy/RCXXDwBPSEB+nsYTavokC5x0SoVVVtXvkOoEvFM3vZWpwId5duxl9Tymd1kKMzhw28ajiQH1eikXACFx/cKPATeHIiKcGMuBG2CYdIch+51vQ3I5AI3PT6NdFSfwEEtBe2JM0TENIvDmpurT+0RwCVfIAYAxGFu3eqjVyIa5iCRIEsfGklSG+c+Ec/K9/Uh1AhOTFSfwcLNApuiYhrLvIcGXBIAxnAqsOwZkaTERDCUSYDi0xF8mlUP837JLRoAOTTUP1O2rXNTDOgd1IqMvPuKBaSDgszVX5c4FQL2HWkNeLkMehFlDsBcw+gMyY1pUgYxkbrq3Y6scByDv/GK8UhB8mACg/EXzOFlCjGFKAsAaZUNrmgArsP5xucFTH9MTJRp1zkA9v8195Qf3KAgYmJ1Gla7QwwQAyfRK60iXBIA1llwHAFQOUEhuHmU4RrIJ4PJKftvVMGBydqRCDXh4AIhR0PS3BhMlVEG0xy/kKs9bAhRb71mzFUwrVHBrLslMKTDmq3/XyKkAhAHz8Qo14CGd6j0xkIqCDn/hUT8jXwAAuR4fpvnotU1AIpXVNswxLgybH+pvVlMB78p0pSv08OoAeF4fhPwKBgLWRC4A9EIkTxkgkBKN1jV9gJ/fIACgSiUjAA0tj6BKV+ghVQIjIBEHJO0gX5jKr88DgGTx5M11rl0KgI+UAwB3NgAKzX/u3qsgYORmvNIVejhnv0Q0W6z1oUJNPbwpiskGgCQE8y47kEquJRsIpJKsVTE5WQDDFaz2HKzTydWAySm0vzIg8jDONlmyA/O7rYWFvrMBwESEfE1pa1BcSwIeiMVaVQAN0VxZV1R4BcwBJCPAOzaIDlRu62GkAXVEujFqDYYKVYTV1Z+ap1poBamHSlnXdAFuTcEpM2qkxJaHi/h4JRVA42MVJ/CQggCi3Ac+IFHYB1DZL5VhUYEFVEZLcg0TAKbEr/kr6mLxdCm4WKa/pVYNBEcqXaGHcmqwbhc2AdGCiaA1lAOAGNIHCvw13l0yCsAmX7s3PCcJoMJ7dxdFqNIVGBiqdIUeSiWgjiWaHUZLtHD6ps9CACWiQr0f2OzEe9YEQMbJGLNjQDpW/HVX71HKAUPeilrkwzi1AgT6fgjRgwV9QHbXBo+HhwMFgwV3KQVgAoCMOJjRmG1YqEiJav+hOgUBcbSrUhB8KNVgGks2FJKKLVCxSaGCm0UAKHQJ4Sgc9PkzQYBmWym5/xQ6VDJTkREQ96KKWtT/+7O5VtDTfjwFWFDswZodr2O954ILCK1GvkQcmAuAqD7bA5RWgt0pJ4NgAirUgIcTBdAMvp3Cci94OiDLWheoBMnRQqg4N4yEEpl9cdkegGKk0v3eTbVqV6BSDHg4xaAwrcebfCzr8AF0wTxwLa0AXPjzpwEAw+hZH1L0rvGwd6ipgK5iAR6OE0jR5HosRax3dhSYKrJezBgNFlENIxpDGgBk9ZgovVS7ppXCqUDcq6tIxj6Us3tvRE9oAZaiOt+a68K6okVoX0FLYTdCKr8ZAKjriuUPSLHetd/1ISwcgCqVgId0YESUoYrJ+EMVN8tgc6j4hklLMKjNBVRFWawSilVBrPlkEJph0XquFVIBhPZXDMDDygUxAvzFwvgsH0CHC9YC1ZPQaE7DflmZAUTciAoAozHz8Rg6FVlfbrd529ZdByoNwYeHgD3gBRQ9EGNJH8Aw+aSgrGHRdMXXGKQpOoHLf1bSUlAAoDEANBUT6tYb2Fde/8P1AnulJENYW3lNgRxqIB1Daf6H0ZizRtxodYfVf28Mpdwp4vjlTEIGQKYTzFDJCNpVieu/KpFgLYpByx7TN7VFXXy7OWm7HsVkYijsHg1F4Vg0IPAkYopkBNy0X5RYvChWbinJAFANAAQdLNqzrfKuvzJny7Y6joIYjk/RGhOP90YZsztCVAzxhNwPwCC/T/GwSNZqVUARTspNHwBAKsYKIEBhlSu/BABqZZFmRKFuZ6XF/1UqB2w+IMA2MCjahSk10QN74AdMgA5IVk9YgijAIsf2suabW08Dp0zGDQUMQYvsApIiJXKU0SP3/nCaYZWtCYNXWddWrP9XDAG768JMwgNXJLoVBID7TyYCHmtO8yZJWoJZvC7AAB+1gBkwJsJJQhKGe0+KjJsNJ5TCHwwaw4SQfP+isLXC8/3qIWA/xzBRD8R8bEp+zdC2S9E8bfFkOwGaRXgUIJvZ6Xa7KWwGgnqR9xixCiSfFPX6WJiR7x8KQbAOntw/xaL9lXm/r2RBKEYxIYjZ9WyS0P8BACDe63YHAwntJA9DRRAfsOaM9xBXwAetfCoZNUZDNKNPiXpGTKaxw9MkZmD0XF2lpPfVPAd0IsOEgjx+uLjphwGQikVDqagnqylIMRFE51E7ZU+QSICoAAP2wE0AEM5sAPLLIaOe21tRgPzKIqCOS/r5BE0xMTEKm2QgzmNEfSBBY9qfFgE0p2ODgYQ7b3TIDzfM6EUR/wEsKKbC4ew/p/WRrZXo7yvsBWr3RNiwm+L5cIw3BjAjLMW68UI/owYBFJ1k61DMEuDdebNDpHOQiomMIggr5t1/Jfn7Sp/qbbVbwQ7woVQsGYLoj6LdbDhU7wkEFL1AhqaTXOzT1V21ET5I6wseJhULU3LGkAUASh/ZVQn/vvJn076ddVyKh2hOz7uT4XAsIkmsmNL7GSzw5RY59rP/OvP+gS3b9rApjdRP9rpHNkkTCxDOkgKJ1FZqf18PO7Bzj8RxEV1d3Z6tT2xFq5998O4777Mci8+7H39y4o0TH8Asx+6aOi6sp5jCCICAMisIrNz/1+rsOLR9+/a/g/ONb/xd3TvHTxx76er9Dz/++OMPPzl25szx4ye+RVx5dc1eWD5KFcAA6MCyfpoRMwLxtD9S6eh/bYpCVVvI2Vy1+Rvf+MauyNUTx47Brcvn+LFjJ64+odzljgO76tiwn6JzQUAlOS6ZjKkGgqHdkcoS2K/N/cPWuczGvi070f3jV1966aVj6XPmQw2PY1/N1jo2qafprK3vFJ9ECLFuNwkdIHKUKuWfryscDq7+6qWrcF6SDxiDE59mFXO27KsBOyCmGFoxBAxNhVn2nXc+eBeCBg7HDtyuitTT1zct+Navr/73Hx9XfcDxYyeOfVad6zKqD+zfGsHJAgXuQC9Cpnj/pWMnXrp//8OPPv3sg3dqKu7/a3x2/u9jP3nhhe8//9xzP/je9/7xjWMnjv9DoXLOjurtO3ftPcyG2ci7H109cwaj5Qw+J858WLH/X+ez/dNjP/wJAOAH+P5f/KkmBsy3Fjt2H6pFN46fwbEiuAsSOBw/8UlF+f/rHBJWv3v1n/7pJy9gC/CD7333jWNn7hef5oLUYV/dx5A1HEtHjcdPvPHTb1UqwF/fs+Xbf7j6ox/9RPEB/3jm2JmPD5SAS9WmvR8ex9Hime+T8/xz3/vui09VWkBf2+vftP8P////+NmPfogBgJ0AhIHfKl7R37wF1L7vAwCu/vNz2GLAP4Dz4h8rFKCv8fv/1a//x89+/k8//f5z333xxV/+8o0zH5aa591S9Y0nPjl29epx+fk/T2Dw4mOV4f6v6/1Xv/urX/3qxz/6pxee/8Fz//HHb/7xj3/8+OmSDn3LN771yUu//vXPX/jJT3/6AoHAD7734mOVNODrevZ98Otf/frnP//xz//rP/++9gBuEWwvrdaz+Rvf+tXVX//6Rz/853/64U8AAhgB2AJURru+njnApv3/8Nl/+29//8QTT9Tsg4LP2te4eXPt/776q1//7Ec//9E/ywh47rlffnN7BQBfWwTsg1O9aZMS463jHrd/Ckbjxz/78c8AAi88//z3n//lCyADUwHA1xcD2WnemmfH0xA2/Pcf/+iHP/nhCz/62U+/9+JzNw6A7dhcgUCZgaHoqfkULMCP7390/8f/68a3/v6Pj904sHlLBQCPDgAgbvjdRx999vff/vYT+4FTcqBmX1XFAZTL/a/vIjcf+vbObX/3jS2boCxU+aE9ch6A/LXN64dL5Xwtnr58Kj+JR/FsUSiCm//f2IfK+bq9/i3fWNf9rwGACjy+1gbg/8b8l5f3+D/DJOLQDyZ7JAAAAABJRU5ErkJggg==", 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
        this.startimage.setImageBitmap(bitmap);
        ((ViewGroup.MarginLayoutParams) this.startimage.getLayoutParams()).topMargin = dp2px(10);
        this.mExpandet.setVisibility(View.GONE);
        this.mExpandet.setBackgroundColor(Color.parseColor("#606060"));
        this.mExpandet.setGravity(17);
        this.mExpandet.setOrientation(LinearLayout.VERTICAL);
        this.mExpandet.setPadding(5, 0, 5, 0);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, -2);
        this.mExpandet.setLayoutParams(layoutParams1);
        ScrollView scrollView = new ScrollView(getBaseContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(190)));
        scrollView.setBackgroundColor(Color.parseColor("#FF000000"));
        this.view1.setLayoutParams(new LinearLayout.LayoutParams(-1, 5));
        this.view1.setBackgroundColor(Color.parseColor("#303030"));
        this.patches.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.patches.setOrientation(LinearLayout.VERTICAL);
        this.view2.setLayoutParams(new LinearLayout.LayoutParams(-1, 5));
        this.view2.setBackgroundColor(Color.parseColor("#303030"));
        this.view2.setPadding(0, 0, 0, 10);
        this.mButtonPanel.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        TextView textView1 = new TextView(getBaseContext());
        textView1.setText(Title());
        textView1.setTextColor(-16777216);
        textView1.setTypeface(Typeface.DEFAULT_BOLD);
        textView1.setTextSize(20.0F);
        textView1.setPadding(10, 25, 10, 5);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams4.gravity = 17;
        textView1.setLayoutParams(layoutParams4);
        TextView textView2 = new TextView(getBaseContext());
        textView2.setText(Html.fromHtml(Credits()));
        textView2.setTextColor(-16777216);
        textView2.setTypeface(Typeface.DEFAULT_BOLD);
        textView2.setTextSize(10.0F);
        textView2.setPadding(10, 5, 10, 10);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams5.gravity = 17;
        textView2.setLayoutParams(layoutParams5);
        (new LinearLayout.LayoutParams(-1, dp(25))).topMargin = dp(2);
        this.rootFrame.addView(this.mRootContainer);
        this.mRootContainer.addView(this.mCollapsed);
        this.mRootContainer.addView(this.mExpandet);
        this.mCollapsed.addView(this.startimage);
        this.mExpandet.addView(textView1);
        this.mExpandet.addView(textView2);
        this.mExpandet.addView(this.view1);
        this.mExpandet.addView(scrollView);
        scrollView.addView(this.patches);
        this.mExpandet.addView(this.view2);
        this.mExpandet.addView(relativeLayout2);
        this.mFloatingView = this.rootFrame;
        if (Build.VERSION.SDK_INT >= 26) {
            this.params = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            this.params = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        }
        WindowManager.LayoutParams layoutParams = this.params;
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 100;
        this.mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.mWindowManager.addView(this.mFloatingView, this.params);
        RelativeLayout relativeLayout1 = this.mCollapsed;
        LinearLayout linearLayout = this.mExpandet;
        this.mFloatingView.setOnTouchListener(onTouchListener());
        this.startimage.setOnTouchListener(onTouchListener());
        initMenuButton(relativeLayout1, linearLayout);
        modMenu();
    }

    private void initMenuButton(final View collapsedView, final View expandedView) {
        this.startimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                collapsedView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
            }
        });
        this.kill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                FloaterDark.this.stopSelf();
            }
        });
        this.close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
    }

    private boolean isViewCollapsed() {
        return (this.mFloatingView == null || this.mCollapsed.getVisibility() == View.VISIBLE);
    }

    private native void changeSeekBar(int paramInt1, int paramInt2);

    private native void changeToggle(int paramInt);

    private native String[] getListFT();

    private void modMenu() {

        String[] listFT = getListFT();
        for (int i2 = 0; i2 < listFT.length; i2++) {
            String str = listFT[i2];
            final int l2 = i2;
            if (str.contains("TG_")) {
                addSwitch(str.replace("TG_", ""), i2, new SW() {
                    public void OnWrite(boolean param1Boolean) { changeToggle(l2); }
                });
            } else {
                String[] arrayOfString1;
                if (str.contains("SB_")) {
                    arrayOfString1 = str.split("_");
                    addSeekBar(arrayOfString1[1], i2, Integer.parseInt(arrayOfString1[2]), Integer.parseInt(arrayOfString1[3]), new SB() {
                        @Override
                        public void OnWrite(int param1Int) {
                            changeSeekBar(l2, param1Int);
                        }
                    });
                } else {
                    addSwitch(str, i2, new SW() {
                        public void OnWrite(boolean param1Boolean) { changeToggle(l2); }
                    });
                }
            }
        }


    }

    private View.OnTouchListener onTouchListener() { return new View.OnTouchListener() {
        final View collapsedView = FloaterDark.this.mCollapsed;

        final View expandedView = FloaterDark.this.mExpandet;

        private float initialTouchX;

        private float initialTouchY;

        private int initialX;

        private int initialY;

        public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            int j;
            int i;
            switch (param1MotionEvent.getAction()) {
                default:
                    return false;
                case 2:
                    params.x = this.initialX + (int)(param1MotionEvent.getRawX() - this.initialTouchX);
                    params.y = this.initialY + (int)(param1MotionEvent.getRawY() - this.initialTouchY);
                    FloaterDark.this.mWindowManager.updateViewLayout(FloaterDark.this.mFloatingView, FloaterDark.this.params);
                    return true;
                case 1:
                    i = (int)(param1MotionEvent.getRawX() - this.initialTouchX);
                    j = (int)(param1MotionEvent.getRawY() - this.initialTouchY);
                    if (i < 10 && j < 10 && FloaterDark.this.isViewCollapsed()) {
                        this.collapsedView.setVisibility(View.GONE);
                        this.expandedView.setVisibility(View.VISIBLE);
                        Toast.makeText(FloaterDark.this, Html.fromHtml("Made by DARK"), Toast.LENGTH_LONG).show();
                    }
                    return true;
                case 0:
                    break;
            }
            this.initialX = params.x;
            this.initialY = params.y;
            this.initialTouchX = param1MotionEvent.getRawX();
            this.initialTouchY = param1MotionEvent.getRawY();
            return true;
        }
    }; }

    public IBinder onBind(Intent paramIntent) { return null; }

    public void onCreate() {
        super.onCreate();
        initFloating();
    }

    public void onDestroy() {
        super.onDestroy();
        View view = this.mFloatingView;
        if (view != null)
            this.mWindowManager.removeView(view);
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        this.expire = paramIntent.getStringExtra("EXPIRY");
        return START_NOT_STICKY;
    }

    public void onTaskRemoved(Intent paramIntent) {
        stopSelf();
        try {
            Thread.sleep(100L);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        super.onTaskRemoved(paramIntent);
    }

    private static interface SB {
        void OnWrite(int param1Int);
    }

    private static interface SW {
        void OnWrite(boolean param1Boolean);
    }
}

