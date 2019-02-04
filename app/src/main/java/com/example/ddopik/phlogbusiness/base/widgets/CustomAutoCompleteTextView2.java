package com.example.ddopik.phlogbusiness.base.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdalla maged
 * on 03,February,2019
 */
public class CustomAutoCompleteTextView2 extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public List<Photographer> currentMentionedPhotoGrapherList = new ArrayList<>();
    public List<Business> currentMentionedBusiness = new ArrayList<>();

    public CustomAutoCompleteTextView2(Context context) {
        super(context);

    }

    public CustomAutoCompleteTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public CustomAutoCompleteTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void replaceText(CharSequence text) {

    }

    @Override
    public void dismissDropDown() {
        super.dismissDropDown();
    }


    public void handleMentionedCommentBody(int mentionedUserPosition, List<MentionedUser> mentionedUserList) {


        //place where we will insert our new mentioned  user
        int searKeyPosition = getText().toString().lastIndexOf("@", getSelectionStart());
        int searchKeysCount = getSelectionStart() - searKeyPosition;
        /**
         * User has selected "Mentioned user" from mentionList After writing The "@" symbol
         * now we convert this selection to the predefined scheme in order to send it through Api
         * then add it to our mentionList for later injecting
         * **/
        if (searKeyPosition >= 0) {

            if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_PHOTOGRAPHER) {
                Photographer photographer = new Photographer();
                photographer.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                photographer.fullName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                photographer.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedPhotoGrapherList.add(photographer);
                //////////

                setUserSpannable(getPhotoGrapherClicableSpanObj(photographer), searKeyPosition, searchKeysCount);

                //////////


            } else if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_BUSINESS) {

                Business business = new Business();
                business.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                business.userName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                business.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedBusiness.add(business);
                setUserSpannable(getBusinessClicableSpanObj(business), searKeyPosition, searchKeysCount);

            }


        }


    }

    private void setUserSpannable(UserClickableSpan userSpannable, int position, int oldSearchKeysCount) {
        CharSequence charSequence = TextUtils.concat(getText());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        int startPosition = position ;

        if (startPosition <= 0) {
            startPosition = 0;
        }
        int endPosition = startPosition + userSpannable.userName.length() - 1;
        spannableStringBuilder.insert(startPosition, userSpannable.userName);
        spannableStringBuilder.setSpan(userSpannable, startPosition, endPosition , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.delete(endPosition+1, endPosition+oldSearchKeysCount+1);
        setClickable(true);
        setMovementMethod(LinkMovementMethod.getInstance());
        setText(spannableStringBuilder);

        setSelection(endPosition);
        dismissDropDown();
    }

    private void removeSearchQuery() {

    }

    private UserClickableSpan getPhotoGrapherClicableSpanObj(Photographer photographer) {

        ///////PhotoGrapher CallBack
        UserClickableSpan photoGrapherClickableSpan = new UserClickableSpan() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                intent.putExtra(UserProfileActivity.USER_ID, photographer.id);
                intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_PHOTOGRAPHER);
                getContext().startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE); // specific color for this link
            }
        };
        photoGrapherClickableSpan.userId = photographer.id.toString();
        photoGrapherClickableSpan.userName = photographer.fullName + " ";
        photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
        return photoGrapherClickableSpan;
    }

    private UserClickableSpan getBusinessClicableSpanObj(Business business) {

        ///////PhotoGrapher CallBack
        UserClickableSpan photoGrapherClickableSpan = new UserClickableSpan() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                intent.putExtra(UserProfileActivity.USER_ID, business.id);
                intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
                getContext().startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.MAGENTA); // specific color for this link
            }
        };
        photoGrapherClickableSpan.userId = business.id.toString();
        photoGrapherClickableSpan.userName = business.firstName+" "+business.lastName + " ";
        photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
        return photoGrapherClickableSpan;
    }

    public String prepareCommentToSend() {

        UserClickableSpan[] clickableSpansList = getText().getSpans(0, getText().length(), UserClickableSpan.class);

        String sendCommentVal = getText().toString();
        String newCommentValue = "";

        UserClickableSpan[] clickableSpansListSorted = sortMentionListRanges(clickableSpansList);

        for (int i = 0; i < clickableSpansListSorted.length; i++) {
            if (clickableSpansListSorted[i].userType.equals(Constants.UserType.USER_TYPE_PHOTOGRAPHER)) {
                int startPoint = getText().getSpanStart(clickableSpansListSorted[i]);
                int endPoint = getText().getSpanEnd(clickableSpansListSorted[i]);

                String before;
                if (i == 0) {
                    before = sendCommentVal.substring(0, startPoint);
                } else {
                    int previousSegmentEndPoint = getText().getSpanEnd(clickableSpansListSorted[i - 1]);
                    before = sendCommentVal.substring(previousSegmentEndPoint, startPoint);
                }
                String mentionedId;
                if (sendCommentVal.substring(endPoint + 1).equals(" ") && (endPoint) < sendCommentVal.length()) {
                    mentionedId = "@0_" + clickableSpansListSorted[i].userId;
                } else {
                    mentionedId = "@0_" + clickableSpansListSorted[i].userId + " ";
                }
                newCommentValue = newCommentValue + before + mentionedId;
            }

        }

        return newCommentValue;
    }

    private UserClickableSpan[] sortMentionListRanges(UserClickableSpan[] clickableSpansList) {


        int len = clickableSpansList.length;
        for (int i = 0; i < clickableSpansList.length; i++) {
            for (int j = 0; j < clickableSpansList.length - 1; j++) {
                UserClickableSpan userClickableSpansTemp;
//                if ((j + 1) >= clickableSpansList.length) {
//                    break;
//                }
                if (getText().getSpanStart(clickableSpansList[j]) > getText().getSpanStart(clickableSpansList[j + 1])) {
                    userClickableSpansTemp = clickableSpansList[j];
                    clickableSpansList[j] = clickableSpansList[j + 1];
                    clickableSpansList[j + 1] = userClickableSpansTemp;

                }


            }
        }
        return clickableSpansList;
    }
}
