package com.example.ddopik.phlogbusiness.base.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView implements TextWatcher {

    public List<Photographer> currentMentionedPhotoGrapherList = new ArrayList<>();
    public List<Business> currentMentionedBusiness = new ArrayList<>();
    private final String USER_MENTION_IDENTIFIER = "%";
    private List<MentionRange> mentionsPoint = new ArrayList<>();


    public CustomAutoCompleteTextView(Context context) {
        super(context);

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this);

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void replaceText(CharSequence text) {

    }


    @Override
    public void dismissDropDown() {
        super.dismissDropDown();
    }

    private int getCursorPosition() {
        int cursorPosition = getSelectionStart();
        boolean isContainImojis = Utilities.isContainImojis(getText().toString());
        if (!isContainImojis) {
            cursorPosition = cursorPosition + 1;
        }
        return cursorPosition;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    private void sortMentionListRanges() {
        for (int i = 0; i < mentionsPoint.size(); i++) {
            for (int j = 0; i < mentionsPoint.size() - i; j++) {
                MentionRange mentionRangeTemp;
                if ((j + 1) >= mentionsPoint.size()) {
                    break;
                } else {
                    if (mentionsPoint.get(j).startPoint > mentionsPoint.get(j + 1).startPoint) {
                        mentionRangeTemp = mentionsPoint.get(j + 1);
                        mentionsPoint.set(j, mentionsPoint.get(j + 1));
                        mentionsPoint.set(j + 1, mentionRangeTemp);

                    }
                }

            }
        }
    }

    /**
     * get old mentioned Segments and append new mentioned user to it
     **/
    private void trackOldMentionsOffset() {

        UserClickableSpan[] clickableSpansList = getText().getSpans(0, getText().length(), UserClickableSpan.class);
        mentionsPoint.clear();

        for (UserClickableSpan userClickableSpan : clickableSpansList) {
            MentionRange mentionRangeNew = new MentionRange();
            mentionRangeNew.userClickableSpan = userClickableSpan;
            mentionRangeNew.startPoint = getText().getSpanStart(userClickableSpan);
            mentionRangeNew.endPoint = getText().getSpanEnd(userClickableSpan);
            mentionsPoint.add(mentionRangeNew);

//            sortMentionListRanges();
//            if (userClickableSpan.userType.equals(Constants.UserType.USER_TYPE_PHOTOGRAPHER)) {
//
//            } else if (userClickableSpan.userType.equals(Constants.UserType.USER_TYPE_BUSINESS)) {
//            }
        }
    }


    private String mergeOldMentionsTag(int newSelectionPoint) {



        String oldCommentText = getText().toString();

        for (MentionRange mentionRange : mentionsPoint) {

            String start="";
            String end = "";
            String mid;
            if (mentionRange.startPoint == newSelectionPoint) {
                int newInsertIndex = oldCommentText.lastIndexOf("@", getCursorPosition());
                if (newInsertIndex != 0) {
                    start = oldCommentText.substring(0, newInsertIndex - 1);
                }
                mid = "@0_" + mentionRange.userClickableSpan.userId+ " ";

                if (oldCommentText.length() - 1 >= getCursorPosition()) {
                    end = oldCommentText.substring(getCursorPosition()) ;
                }
                oldCommentText = start + mid + end;
            } else {
                start = oldCommentText.substring(0, mentionRange.startPoint);

                mid = "@0_" + mentionRange.userClickableSpan.userId+" ";

                end = oldCommentText.substring(mentionRange.startPoint+mid.length());
            }
            oldCommentText = start + mid + end;

        }
        return oldCommentText;
}

    public void handleMentionedCommentBody(int mentionedUserPosition, List<MentionedUser> mentionedUserList) {


        ///initialize mentionPointArray with old spans range if existed
        trackOldMentionsOffset();
        int searKeyPosition = getText().toString().lastIndexOf("@", getSelectionStart());

        /**
         * User has selected "Mentioned user" from mentionList After writing The "@" symbol
         * now we convert this selection to the predefined scheme in order to send it through Api
         * then add it to our mentionList for later injecting
         * **/
        if (searKeyPosition >= 0) {
            String replacement = "";
            if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_PHOTOGRAPHER) {
                replacement = ("@" + "0" + "_" + mentionedUserList.get(mentionedUserPosition).mentionedUserId+USER_MENTION_IDENTIFIER);
                Photographer photographer = new Photographer();
                photographer.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                photographer.fullName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                photographer.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedPhotoGrapherList.add(photographer);
                //////////
                MentionRange mentionRange = new MentionRange();
                mentionRange.startPoint = getSelectionStart();
                mentionRange.endPoint = mentionRange.startPoint + replacement.length() - 1;
                mentionRange.userClickableSpan = getPhotoGrapherClicableSpanObj(photographer);
                this.mentionsPoint.add(mentionRange);

                String oldMentionValue = mergeOldMentionsTag(mentionRange.startPoint);
                setText(oldMentionValue);
                //////////


            } else if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_BUSINESS) {
                replacement = ("@" + "1" + "_" + mentionedUserList.get(mentionedUserPosition).mentionedUserId);
                Business business = new Business();
                business.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                business.userName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                business.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedBusiness.add(business);

            }


        }


/////////////////////////////////
        //Extract All  mentioned users from Comment_Text
//        //todo second mention not included start
        List<String> mentionedUsersId = Utilities.getMentionsList(getText().toString());
//        //
        List<String> mentionsPhotoGrapherIdIdList = new ArrayList<>();
        List<String> mentionBusinessIdList = new ArrayList<>();

        /**
         *and convert mentionsTagsID_list to standard ID_list
         **/
        if (mentionedUsersId.size() > 0) {
            for (String authorId : mentionedUsersId) {
                String[] singleId = authorId.split("\\_");

                if (singleId[0].equals("0")) {
                    mentionsPhotoGrapherIdIdList.add(singleId[1]);
                } else if (singleId[0].equals("1")) {
                    mentionBusinessIdList.add(singleId[1]);
                }
            }

            mentionsPoint.clear();
            setPhotoGrapherMentionSpan(mentionsPhotoGrapherIdIdList);
////////////////////////
//            commentFinalValue = replaceMentionedBusinessFlag(mentionBusinessIdList, commentFinalValue);
//            commentFinalValue = setBusinessMentionSpan(mentionBusinessIdList, commentFinalValue);
            ////
            makeSpannableLinks();
        } else {
            setText(getText());
        }

        dismissDropDown();
        mentionsPoint.clear();

    }


    /**
     * Replaces PhotoGrapher @0_user__id with matched PhotoGrapherName  appended with predefined flag for later processing
     */
    private void replaceMentionedPhotoGrapherFlag(List<String> mentionsPhotoGrapherIdIdList) {
        /// Append unique identifier to mentioned user to get highLighted later
        /// And Replacing All Occurrence of photoGrapherId with actualValue
        for (String photoGrapherId : mentionsPhotoGrapherIdIdList) {
            Photographer photographer = getMentionedPhotoGrapher(photoGrapherId);
            if (photographer != null) {
                setText( getText().toString().replace("@0_" + photoGrapherId, " " + photographer.fullName + USER_MENTION_IDENTIFIER));

            }
        }
    }

    /**
     * Replaces Business @1_user__id with matched BusinessName appended with predefined flag for later processing
     */
    private String replaceMentionedBusinessFlag(List<String> mentionBusinessIdList, String commentFinalValue) {
        /// Append unique identifier to mentioned user to get highLighted later
        /// And Replacing All Occurrence of businessId with actualValue
        for (String businessId : mentionBusinessIdList) {
            if (getMentionedBusiness(businessId) != null) {
                Business business = getMentionedBusiness(businessId);
                if (business != null) {
                    commentFinalValue = commentFinalValue.replace("@1_" + businessId, business.firstName + " " + business.lastName + USER_MENTION_IDENTIFIER);
                    setText(commentFinalValue);
                }
            }
        }
        return commentFinalValue;

    }

    private void setPhotoGrapherMentionSpan(List<String> mentionsPhotoGrapherIdIdList) {


        /// Append unique identifier to mentioned user to get highLighted later
        /// And Replacing All Occurrence of photoGrapherId with actualValue
        for (String photoGrapherId : mentionsPhotoGrapherIdIdList) {
            Photographer photographer = getMentionedPhotoGrapher(photoGrapherId);
            if (photographer != null) {
//                for (String photographerId : mentionsPhotoGrapherIdIdList) {
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
                    photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
                    String replacement = photographer.fullName + USER_MENTION_IDENTIFIER;
                    setText(getText().toString().replaceFirst("@0_" + photoGrapherId, " " + photographer.fullName + USER_MENTION_IDENTIFIER));
                    //new userRange get initialized start from here
                    MentionRange mentionRange = new MentionRange();
                    mentionRange.startPoint = getText().toString().indexOf(replacement);
                    mentionRange.endPoint = mentionRange.startPoint + photographer.fullName.length() + 1;
                    mentionRange.userClickableSpan = photoGrapherClickableSpan;
                    mentionsPoint.add(mentionRange);
                    setText(getText().toString().replaceFirst(replacement, replacement.substring(0, replacement.length() - 1) + " "));

//                }

            }
        }


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
        photoGrapherClickableSpan.userName = photographer.fullName;
        photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
        return photoGrapherClickableSpan;
    }

    private String setBusinessMentionSpan(List<String> mentionBusinessIdList, String commentFinalValue) {

        for (String businessId : mentionBusinessIdList) {
            if (getMentionedBusiness(businessId) != null) {
                Business business = getMentionedBusiness(businessId);
                //////business CallBack
                UserClickableSpan businessClickableSpan = new UserClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
                        intent.putExtra(UserProfileActivity.USER_ID, businessId);
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
                businessClickableSpan.userId = businessId;
                businessClickableSpan.userType = Constants.UserType.USER_TYPE_BUSINESS;

                String replacement = business.firstName + " " + business.lastName + USER_MENTION_IDENTIFIER;

                MentionRange mentionRange = new MentionRange();
                mentionRange.startPoint = commentFinalValue.indexOf(replacement);
                mentionRange.endPoint = replacement.length() - 1;

                mentionsPoint.add(mentionRange);

                int tempStart = (replacement.length()) - commentFinalValue.indexOf(replacement);
                int tempEnd = commentFinalValue.indexOf(replacement);
                getText().replace(tempStart, tempEnd, replacement);

                commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
            }
        }
        return commentFinalValue;
    }


    /**
     //     * @param viewHolder        --->view holder contain comment value
     //     * @param mentionsList      --->replacement user_value flaged with (?*_)
     //     * @param clickableSpanList --->link CallBack
     */
    private void makeSpannableLinks() {

        SpannableString spannableString = new SpannableString(getText());
        for (int i = 0; i < mentionsPoint.size(); i++) {
            spannableString.setSpan(mentionsPoint.get(i).userClickableSpan, mentionsPoint.get(i).startPoint, mentionsPoint.get(i).endPoint - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//        setLinksClickable(true);
        setClickable(true);
        setMovementMethod(LinkMovementMethod.getInstance());

        setText(spannableString);


//        SpannableString spannableString = new SpannableString(getText());
//        for (int i = 0; i < mentionsPoint.size(); i++) {
//            spannableString.setSpan(mentionsPoint.get(i).userClickableSpan, mentionsPoint.get(i).startPoint + 1, mentionsPoint.get(i).endPoint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        setLinksClickable(true);
//        setClickable(true);
//        setMovementMethod(LinkMovementMethod.getInstance());
//        setText(spannableString);


//     setPhotoGrapherMentionSpan();


//        for (MentionRange mentionRange : mentionsPoint) {
//
//            for (int i = mentionRange.startPoint; i <= mentionRange.endPoint - 1; i++) {
//                if (mentionRange.endPoint < currentComment.length) {
//                    currentComment[i] = "";
//                }
//
//            }
//
//        }

//        setText("");


//        SimpleSpanBuilder ssb = new SimpleSpanBuilder();
//
//        for (int i = 0; i < currentComment.length; i++) {
//
//            for (int j = 0; j < mentionsPoint.size(); j++) {
//
//                if (i == mentionsPoint.get(j).startPoint) {
//
//                    ssb.append(mentionsPoint.get(j).userClickableSpan.userName, mentionsPoint.get(j).userClickableSpan);
//                    break;
//                }
//
//                if (j == mentionsPoint.size() - 1) {
//                    ssb.append(currentComment[i], new ForegroundColorSpan(Color.WHITE));
//                }
//
//            }
//
//        }
//        setText(ssb.build());


//        SpannableString spannableString = new SpannableString(getText());
//        for (int i = 0; i < mentionsPoint.size(); i++) {
//            spannableString.setSpan(mentionsPoint.get(i).userClickableSpan, mentionsPoint.get(i).startPoint, mentionsPoint.get(i).endPoint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        setText(newSpannableString);

    }

    private SpannableString convertTextToSpan(String text, UserClickableSpan userClickableSpan) {

        SpannableString spannableString = new SpannableString(text);

        if (userClickableSpan != null) {
            spannableString.setSpan(userClickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            return spannableString;
        }
        return spannableString;
    }

    /**
     * use this method to Span multiple Links in one row
     */
    private void makeSpannableLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];

            String link = links[i];
            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setHighlightColor(Color.TRANSPARENT); // prevent TextView change background when highlight
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }


    private Photographer getMentionedPhotoGrapher(String userId) {
        for (Photographer photographer : currentMentionedPhotoGrapherList) {
            if (photographer.id == Integer.parseInt(userId)) {
                return photographer;
            }
        }
        return null;
    }

    private Business getMentionedBusiness(String userId) {
        for (Business business : currentMentionedBusiness) {
            if (business.id == Integer.parseInt(userId)) {
                return business;
            }
        }
        return null;
    }

    private class MentionRange {
        UserClickableSpan userClickableSpan;
        int startPoint;
        int endPoint;
    }


}