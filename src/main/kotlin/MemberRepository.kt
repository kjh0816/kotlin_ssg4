class MemberRepository {
    fun getMemberByLoginId(loginId: String): Member? {
        for(member in members){
            if(member.loginId == loginId){
                return member
            }
        }

        return null
    }

    fun join(
        loginId: String,
        loginPw: String,
        name: String,
        nickname: String,
        cellphoneNo: String,
        email: String
    ): Int {

        val id = ++memberLastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        members.add(Member(id, loginId, loginPw, regDate, updateDate, name, nickname, cellphoneNo, email))

        return id
    }

    fun getMemberById(memberId: Int): Member? {
        for(member in members){
            if(member.id == memberId){
                return member
            }
        }
        return null
    }


    var memberLastId = 0
    val members = mutableListOf<Member>()
}