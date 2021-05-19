class ArticleRepository {
    fun write(memberId: Int, boardId: Int, title: String, body: String): Int {

        val id = ++articleLastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(id, regDate, updateDate, memberId, boardId, title, body))

        return id


    }

    fun getArticleById(id: Int): Article? {
        for(article in articles){
            if(article.id == id){
                return article
            }
        }
        return null
    }

    fun delete(article: Article) {
        articles.remove(article)
    }

    fun modify(article: Article, title: String, body: String) {
        article.updateDate = Util.getNowDateStr()
        article.title = title
        article.body = body
    }

    fun getFilteredArticles(page: Int, searchKeyword: String, boardCode: String, itemCountInAPage: Int): List<Article> {
        val filtered1Articles = getSearchKeywordFilteredArticles(articles, searchKeyword, boardCode)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemCountInAPage)

        return filtered2Articles
    }

    private fun getSearchKeywordFilteredArticles(articles: MutableList<Article>, searchKeyword: String, boardCode: String): List<Article> {

        val filteredArticle = mutableListOf<Article>()

        val boardId = boardRepository.getBoardIdByCode(boardCode)

        if(searchKeyword.isNotEmpty() && boardId != 0){
            for(article in articles){
                if(article.title.contains(searchKeyword) && article.boardId == boardId){
                    filteredArticle.add(article)
                }
            }
            return filteredArticle
        }
        else if(searchKeyword.isNotEmpty() && boardId == 0){
            for(article in articles){
                if(article.title.contains(searchKeyword)){
                    filteredArticle.add(article)
                }
            }
            return filteredArticle
        }
        else if(searchKeyword.isEmpty() && boardId != 0){
            for(article in articles){
                if(article.boardId == boardId){
                    filteredArticle.add(article)
                }
            }
            return filteredArticle
        }


            return articles


    }

    private fun getPageFilteredArticles(filtered1Articles: List<Article>, page: Int, itemCountInAPage: Int): List<Article> {

        val filteredArticles = mutableListOf<Article>()

        val offsetCount = (page - 1) * itemCountInAPage


        val startIndex = filtered1Articles.lastIndex - offsetCount
        var endIndex = startIndex - (itemCountInAPage - 1)

        if(endIndex < 0){
            endIndex = 0
        }

        for(i in startIndex downTo endIndex){
            filteredArticles.add(filtered1Articles[i])
        }

        return filteredArticles

    }


    var articleLastId = 0
    val articles = mutableListOf<Article>()
}