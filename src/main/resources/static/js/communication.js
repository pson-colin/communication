/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, content, 1);
}

function comment2target(targetId, content, type) {
    if (!content) {
        alert("回复内容不能为空~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=597f22d0a6adce2ca49b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId,content,2);
}
/**
 * 展开二级评论列表
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    if (comments.hasClass("in")) {
        //折叠二级评论
        comments.removeClass("in");
        e.classList.remove("active");
    } else {
        $.getJSON( "/comment/" + id, function( data ) {
            var commentBody = $("#comment-body-" + id);
            var items = [];
            $.each(data.data, function (comment) {
                var c = $("<div/>",{
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html: comment.content
                });
                items.push(c);
            });
            commentBody.append($("<div/>",{
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                "id": "comment-" + id,
                html: items.join("")
            }));

            //展开二级评论
            comments.addClass("in");
            e.classList.add("active");
        });

    }
}