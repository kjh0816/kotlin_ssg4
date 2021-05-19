

class ArticleController {
    fun write(req: Req) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
            return
        }
        println("게시판 번호를 선택해주세요.")
        boardRepository.getList()
        val boardId: Int
        try{
            boardId = readLineTrim().toInt()
        }catch(e: NumberFormatException){
            println("게시판 번호는 숫자만 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(boardId)
        if(board == null){
            println("${boardId}번 게시판은 존재하지 않습니다.")
            return
        }
        println("게시물 제목을 입력해주세요.")
        val title = readLineTrim()
        println("게시물 내용을 입력해주세요.")
        val body = readLineTrim()

        val id = articleRepository.write(loginedMember!!.id, boardId, title, body)

        println("${id}번 게시물이 추가되었습니다.")

    }

    fun delete(req: Req) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
            return
        }
        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("게시물 번호를 숫자로 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }
        if(loginedMember!!.id != article.memberId){
            println("권한이 없습니다.")
            return
        }

        articleRepository.delete(article)
        println("${id}번 게시물이 삭제되었습니다.")

    }

    fun modify(req: Req) {

        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
            return
        }
        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("게시물 번호를 숫자로 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }
        if(loginedMember!!.id != article.memberId){
            println("권한이 없습니다.")
            return
        }

        println("게시판 번호를 선택해주세요.")
        boardRepository.getList()
        val boardId: Int
        try{
            boardId = readLineTrim().toInt()
        }catch(e: NumberFormatException){
            println("게시판 번호는 숫자만 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(boardId)
        if(board == null){
            println("${boardId}번 게시판은 존재하지 않습니다.")
            return
        }
        println("새 게시물 제목을 입력해주세요.")
        val title = readLineTrim()
        println("새 게시물 내용을 입력해주세요.")
        val body = readLineTrim()

        articleRepository.modify(article, title, body)
        println("${id}번 게시물이 수정되었습니다.")

    }

    fun detail(req: Req) {

        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("게시물 번호를 숫자로 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }

        val member = memberRepository.getMemberById(article.memberId)
        val board = boardRepository.getBoardById(article.boardId)

        println("번호: ${article.id}")
        println("제목: ${article.title}")
        println("내용: ${article.body}")
        println("작성자: ${member!!.nickname}")
        println("게시판: ${board!!.name}")
        println("생성날짜: ${article.regDate}")
        println("수정날짜: ${article.updateDate}")

    }

    fun list(req: Req) {

        val page = req.getIntParam("page", 1)
        val searchKeyword = req.getStrParam("searchKeyword", "")
        val boardCode = req.getStrParam("boardCode", "")

        val itemCountInAPage = 10

        val filteredArticles = articleRepository.getFilteredArticles(page, searchKeyword, boardCode, itemCountInAPage)

        println("번호 / 제목 / 작성자 / 게시판 / 생성날짜 ")
        for(article in filteredArticles){
            val member = memberRepository.getMemberById(article.memberId)
            val board = boardRepository.getBoardById(article.boardId)
            println("${article.id} / ${article.title} / ${article.body} / ${member!!.nickname} / ${board!!.name} / ${article.regDate}")
        }
    }
}