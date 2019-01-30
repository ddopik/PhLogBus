package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.base.widgets.UserClickableSpan;
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
    List<ClickableSpan> allCommentClickableSpanList = new ArrayList<>();


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

    private String getCommentSymbol(String[] currentCommentValue, int cursorPosition) {

        // in case "First" char after "@" symbol
        return currentCommentValue[cursorPosition - 2];

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

    /**
     * get old mentioned Segments and append new mentioned user to it
     **/
    private void trackOldMentionsOffset() {

        UserClickableSpan[] clickableSpansList = getText().getSpans(0, getText().length(), UserClickableSpan.class);
        String updateComment = getText().toString();


        for (UserClickableSpan userClickableSpan : clickableSpansList) {
            MentionRange mentionRange = new MentionRange();
            mentionRange.startPoint = getText().getSpanStart(userClickableSpan) - 1;
            mentionRange.endPoint = getText().getSpanEnd(userClickableSpan);


            if (userClickableSpan.userType.equals(Constants.UserType.USER_TYPE_PHOTOGRAPHER)) {


//                UserClickableSpan photoGrapherClickableSpan = new UserClickableSpan() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
//                        intent.putExtra(UserProfileActivity.USER_ID, userClickableSpan.userId);
//                        intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_PHOTOGRAPHER);
//                        getContext().startActivity(intent);
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setUnderlineText(false);
//                        ds.setColor(Color.BLUE); // specific color for this link
//                    }
//                };
//                photoGrapherClickableSpan.userId = userClickableSpan.userId;
//                photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
//                this.allCommentClickableSpanList.add(photoGrapherClickableSpan);
//                this.mentionsPoint.add(mentionRange);

            } else if (userClickableSpan.userType.equals(Constants.UserType.USER_TYPE_BUSINESS)) {
                UserClickableSpan businessClickableSpan = new UserClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
                        intent.putExtra(UserProfileActivity.USER_ID, userClickableSpan.userId);
                        intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
                        getContext().startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(Color.BLUE); // specific color for this link
                    }
                };
                businessClickableSpan.userId = userClickableSpan.userId;
                businessClickableSpan.userType = Constants.UserType.USER_TYPE_BUSINESS;
                this.allCommentClickableSpanList.add(businessClickableSpan);
                this.mentionsPoint.add(mentionRange);
            }
        }

        // make sure to clear mentionsPoint As previous mentions may changed
//        if (mentionsPoint.size() > 0)
//            this.mentionsPoint.clear();
//        if (allCommentClickableSpanList.size() > 0)
//            this.allCommentClickableSpanList.clear();
//        ///

    }

    public void handleMentionedCommentBody(int mentionedUserPosition, List<MentionedUser> mentionedUserList) {


        List<MentionedUser> mentionedUserListTemp = new ArrayList<>();
        mentionedUserListTemp.addAll(mentionedUserList);

        int cursorPosition = getCursorPosition();
        // we must convert our getText() value to char in seek of to find the "@" symbol
        String currentCommentArrVal[] = getText().toString().split("");
        String commentSymbol = getCommentSymbol(currentCommentArrVal, cursorPosition);


        //get between comment Symbol and current cursor
        int commentSymbolPosition = 0;
        String mentionSearchKey;
        for (int i = cursorPosition - 1; i >= 0; i--) {
            if (currentCommentArrVal[i].equals("@")) {
                commentSymbolPosition = i;

                break;
            }
        }


        if (commentSymbolPosition == currentCommentArrVal.length) {
            mentionSearchKey = getText().toString().substring(commentSymbolPosition - 1, cursorPosition);
        }
// else if (commentSymbolPosition < 0) {
//            //comment End or comment mid
//            mentionSearchKey = getText().toString().substring(commentSymbolPosition - 1, cursorPosition-1);
//        } else {
//            //comment start
//            mentionSearchKey = getText().toString().substring(0, cursorPosition);
//        }


        ///Extracting (PhotoGrapher && Business) From Server_MentionsList and build Detailed list for each type
        /**
         * User has selected "Mentioned user" from mentionList After writing The "@" symbol
         * now we convert this selection to the predefined scheme in order to send it through Api
         * **/
        if (commentSymbol.equals("@")) {
            String replacement = "";
            if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_PHOTOGRAPHER) {
                replacement = ("@" + "0" + "_" + mentionedUserList.get(mentionedUserPosition).mentionedUserId);
                Photographer photographer = new Photographer();
                photographer.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                photographer.fullName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                photographer.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedPhotoGrapherList.add(photographer);
            } else if (mentionedUserList.get(mentionedUserPosition).mentionedType == Constants.UserType.USER_TYPE_BUSINESS) {
                replacement = ("@" + "1" + "_" + mentionedUserList.get(mentionedUserPosition).mentionedUserId);
                Business business = new Business();
                business.id = mentionedUserList.get(mentionedUserPosition).mentionedUserId;
                business.userName = mentionedUserList.get(mentionedUserPosition).mentionedUserName;
                business.mentionedImage = mentionedUserList.get(mentionedUserPosition).mentionedImage;
                currentMentionedBusiness.add(business);
            }


//            set mentionReplacement
            currentCommentArrVal[cursorPosition - 2] = replacement;
            currentCommentArrVal[cursorPosition - 1] = " ";


        }

        StringBuilder newCommentStringBuilder = new StringBuilder();
        for (String aCurrentCommentArrVal : currentCommentArrVal) {
            newCommentStringBuilder.append(aCurrentCommentArrVal);
        }


        String commentFinalValue = newCommentStringBuilder.toString();
        //Extract All  mentioned users from Comment_Text
        List<String> mentionedUsersId = Utilities.getMentionsList(commentFinalValue);
        //
        List<String> mentionsPhotoGrapherIdIdList = new ArrayList<>();
        List<String> mentionBusinessIdList = new ArrayList<>();

        /**
         *Separate All mentioned users from Comment_Text
         *and convert mentionsTags to standard ID
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
////////////////////////


            commentFinalValue = replaceMentionedPhotoGrapherFlag(mentionsPhotoGrapherIdIdList, commentFinalValue);
            commentFinalValue = replaceMentionedBusinessFlag(mentionBusinessIdList, commentFinalValue);
            /////
            commentFinalValue = setPhotoGrapherMentionSpan(mentionsPhotoGrapherIdIdList, commentFinalValue);
            commentFinalValue = setBusinessMentionSpan(mentionBusinessIdList, commentFinalValue);
            ////
            makeSpannableLinks(this, this.mentionsPoint, this.allCommentClickableSpanList, commentFinalValue);
        } else {
            setText(commentFinalValue);
        }


//        int finalCursorPosition=cursorPosition+mentionedUserListTemp.get(mentionedUserPosition).mentionedUserName.length();
//        setSelection(finalCursorPosition);
        dismissDropDown();


    }


    /**
     * Replaces PhotoGrapher @0_user__id with matched PhotoGrapherName  appended with predefined flag for later processing
     */
    private String replaceMentionedPhotoGrapherFlag(List<String> mentionsPhotoGrapherIdIdList, String commentFinalValue) {
        /// Append unique identifier to mentioned user to get highLighted later
        /// And Replacing All Occurrence of photoGrapherId with actualValue
        for (String photoGrapherId : mentionsPhotoGrapherIdIdList) {
            Photographer photographer = getMentionedPhotoGrapher(photoGrapherId);
            if (photographer != null) {
                commentFinalValue = commentFinalValue.replace("@0_" + photoGrapherId, photographer.fullName + USER_MENTION_IDENTIFIER);
                setText(commentFinalValue);
            }
        }
        return commentFinalValue;

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

    private String setPhotoGrapherMentionSpan(List<String> mentionsPhotoGrapherIdIdList, String commentFinalValue) {

        for (String photographerId : mentionsPhotoGrapherIdIdList) {
            if (getMentionedPhotoGrapher(photographerId) != null) {
                Photographer photographer = getMentionedPhotoGrapher(photographerId);
                ///////PhotoGrapher CallBack
                UserClickableSpan photoGrapherClickableSpan = new UserClickableSpan() {


                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
                        intent.putExtra(UserProfileActivity.USER_ID, photographerId);
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
                photoGrapherClickableSpan.userId = photographerId;
                photoGrapherClickableSpan.userType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;

                String replacement = photographer.fullName + USER_MENTION_IDENTIFIER;


                MentionRange mentionRange = new MentionRange();
                mentionRange.startPoint = commentFinalValue.indexOf(replacement);
                mentionRange.endPoint = replacement.length() - 1;

                this.mentionsPoint.add(mentionRange);
                this.allCommentClickableSpanList.add(photoGrapherClickableSpan);


                commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
            }
        }
        return commentFinalValue;

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
                this.allCommentClickableSpanList.add(businessClickableSpan);

                int tempStart = (replacement.length()) - commentFinalValue.indexOf(replacement);
                int tempEnd = commentFinalValue.indexOf(replacement);
                getText().replace(tempStart, tempEnd, replacement);

                commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
            }
        }
        return commentFinalValue;
    }


    /**
     * @param viewHolder        --->view holder contain comment value
     * @param mentionsList      --->replacement user_value flaged with (?*_)
     * @param clickableSpanList --->link CallBack
     * @param commentFinalValue --->all text value insideViewHolder
     */
    private void makeSpannableLinks(TextView viewHolder, List<MentionRange> mentionsList, List<ClickableSpan> clickableSpanList, String commentFinalValue) {


        SpannableString spannableString = new SpannableString(commentFinalValue);
        for (int i = 0; i < mentionsList.size(); i++) {
            spannableString.setSpan(clickableSpanList.get(i), mentionsList.get(i).startPoint + 1, mentionsList.get(i).endPoint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        viewHolder.setLinksClickable(true);
        viewHolder.setClickable(true);
        viewHolder.setMovementMethod(LinkMovementMethod.getInstance());


        viewHolder.setText(spannableString);

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
        textView.setHighlightColor(
                Color.TRANSPARENT); // prevent TextView change background when highlight
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
        int startPoint;
        int endPoint;
    }


}