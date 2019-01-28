package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public List<Photographer> currentMentionedPhotoGrapherList = new ArrayList<>();
    public List<Business> currentMentionedBusiness = new ArrayList<>();
    private SpannableString currentSpandapleText;
    private final String USER_MENTION_IDENTIFIER = "%";

    public CustomAutoCompleteTextView(Context context) {
        super(context);

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void replaceText(CharSequence text) {
//        super.replaceText(this.getText().append(text) );
//        handleMentionedCommentBody();
    }


    @Override
    public void dismissDropDown() {
        super.dismissDropDown();
    }


    //    StyleSpan[] ss = e.getSpans(0,e.length(),StyleSpan.class);
//
//for(StyleSpan span : ss){
//        int start = e.getSpanStart(span);
//        int end = e.getSpanEnd(span);
//    }
    private int getCursorPosition() {
        int cursorPosition = getSelectionStart();        ////
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

    public void handleMentionedCommentBody(int mentionedUserPosition, List<MentionedUser> mentionedUserList) {


        int cursorPosition = getCursorPosition();
        String currentCommentArrVal[] = getText().toString().split("");
        String commentSymbol = getCommentSymbol(currentCommentArrVal, cursorPosition);

        //check if selected position meant for mention
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


            ///Replacing "@" with userId_identifier_symbol ex{ @0_18 ,@1_236}
            currentCommentArrVal[cursorPosition - 2] = replacement;
            currentCommentArrVal[cursorPosition - 1] = " ";


        }

        StringBuilder newCommentStringBuilder = new StringBuilder();
        for (String aCurrentCommentArrVal : currentCommentArrVal) {
            newCommentStringBuilder.append(aCurrentCommentArrVal);
        }


        String commentFinalValue = newCommentStringBuilder.toString();
        //Extract mentioned value from Comment_Text
        List<String> mentionedUsersId = Utilities.getMentionsList(commentFinalValue);
        List<String> mentionsPhotoGrapherIdIdList = new ArrayList<>();
        List<String> mentionBusinessIdList = new ArrayList<>();
        List<MentionRange> mentionsPoint = new ArrayList<>();
        List<ClickableSpan> clickableSpanList = new ArrayList<>();


        if (mentionedUsersId.size() > 0) {
            for (String authorId : mentionedUsersId) {
                String[] singleId = authorId.split("\\_");

                if (singleId[0].equals("0")) {
                    mentionsPhotoGrapherIdIdList.add(singleId[1]);
                } else if (singleId[0].equals("1")) {
                    mentionBusinessIdList.add(singleId[1]);
                }
            }


            ///// Must be in A sensational order
            commentFinalValue = replaceMentionedPhotoGrapherFlag(mentionsPhotoGrapherIdIdList, commentFinalValue);
            commentFinalValue = replaceMentionedBusinessFlag(mentionBusinessIdList, commentFinalValue);
            /////






//            if (setPhotoGrapherMentionSpan(mentionsPhotoGrapherIdIdList, commentFinalValue, mentionsPoint).size() > 0) {
//                clickableSpanList.addAll(setPhotoGrapherMentionSpan(mentionsPhotoGrapherIdIdList, commentFinalValue, mentionsPoint));
//            }

             for (String photographerId : mentionsPhotoGrapherIdIdList) {
                if (getMentionedPhotoGrapher(photographerId) != null) {
                    Photographer photographer = getMentionedPhotoGrapher(photographerId);
                    ///////PhotoGrapher CallBack
                    ClickableSpan noUnderLineClickSpan = new ClickableSpan() {
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


                    String replacement = photographer.fullName + USER_MENTION_IDENTIFIER;
                    int replacementStart = commentFinalValue.indexOf(replacement) - 1;
                    int replacementEnd = replacementStart + replacement.length();
                    MentionRange mentionRange = new MentionRange();
                    mentionRange.startPoint = replacementStart;
                    mentionRange.endPoint = replacementEnd;
                    mentionsPoint.add(mentionRange);

                    commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
                    clickableSpanList.add(noUnderLineClickSpan);
                }
            }




            for (String businessId : mentionBusinessIdList) {
            if (getMentionedBusiness(businessId) != null) {
                Business business = getMentionedBusiness(businessId);
                //////business CallBack
                ClickableSpan noUnderLineClickSpan2 = new ClickableSpan() {
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
                String replacement = business.firstName + " " + business.lastName + USER_MENTION_IDENTIFIER;
                int replacementStart = commentFinalValue.indexOf(replacement) - 1;
                int replacementEnd = replacementStart + replacement.length();
                MentionRange mentionRange = new MentionRange();
                mentionRange.startPoint = replacementStart;
                mentionRange.endPoint = replacementEnd;
                mentionsPoint.add(mentionRange);

                commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
                clickableSpanList.add(noUnderLineClickSpan2);
            }
        }




//            if (setBusinessMentionSpan(mentionBusinessIdList, commentFinalValue, mentionsPoint).size() > 0) {
//                clickableSpanList.addAll(setBusinessMentionSpan(mentionBusinessIdList, commentFinalValue, mentionsPoint));
//            }




            makeSpannableLinks(this, mentionsPoint, clickableSpanList, commentFinalValue);

        } else {
            setText(commentFinalValue);
        }

        //////////////
        dismissDropDown();
    }


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

//    private List<ClickableSpan> setPhotoGrapherMentionSpan(List<String> mentionsPhotoGrapherIdIdList, String commentFinalValue, List<MentionRange> mentionsPoint) {
//
//
//        return clickableSpanList;
//    }

//    private List<ClickableSpan> setBusinessMentionSpan(List<String> mentionBusinessIdList, String commentFinalValue, List<MentionRange> mentionsPoint) {
//        List<ClickableSpan> clickableSpanList = new ArrayList<>();
//        for (String businessId : mentionBusinessIdList) {
//            if (getMentionedBusiness(businessId) != null) {
//                Business business = getMentionedBusiness(businessId);
//                //////business CallBack
//                ClickableSpan noUnderLineClickSpan2 = new ClickableSpan() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
//                        intent.putExtra(UserProfileActivity.USER_ID, businessId);
//                        intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
//                        getContext().startActivity(intent);
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setUnderlineText(false);
//                        ds.setColor(Color.MAGENTA); // specific color for this link
//                    }
//                };
//                String replacement = business.firstName + " " + business.lastName + USER_MENTION_IDENTIFIER;
//                int replacementStart = commentFinalValue.indexOf(replacement) - 1;
//                int replacementEnd = replacementStart + replacement.length();
//                MentionRange mentionRange = new MentionRange();
//                mentionRange.startPoint = replacementStart;
//                mentionRange.endPoint = replacementEnd;
//                mentionsPoint.add(mentionRange);
//
//                commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
//                clickableSpanList.add(noUnderLineClickSpan2);
//            }
//        }
//        return clickableSpanList;
//    }



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