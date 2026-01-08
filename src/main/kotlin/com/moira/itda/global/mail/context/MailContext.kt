package com.moira.itda.global.mail.context

object MailContext {
    // 본인인증 메일 제목
    const val IDENTIFY_MAIL_SUBJECT = "[ITDA] 회원가입 본인인증 코드입니다."

    // 본인인증 메일 내용
    const val IDENTIFY_MAIL_TEXT = "본인인증을 위한 6자리 코드 [ %s ]를 입력해주세요."

    // 피드백 답변 제목
    const val FEEDBACK_ANSWER_SUBJECT = "[ITDA] '%s' 관련 문의사항에 대한 관리자의 답변이 등록되었습니다."

    // 피드백 답변 내용
    const val FEEDBACK_ANSWER_TEXT = """
        제공해주신 '%s' 관련 문의사항에 대한 답변입니다.
        --
        %s
    """
}