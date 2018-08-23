接口均来自wanAndroid的开放api

1.登录
http://www.wanandroid.com/user/login

方法：POST
参数：
	username，password

2.注册
http://www.wanandroid.com/user/register

方法：POST
参数
	username,password,repassword

登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。

注意所有TODO相关都需要登录操作，建议登录将返回的cookie（其中包含账号、密码）持久化到本地即可。
3.TODO列表

    http://wanandroid.com/lg/todo/list/0/json
    http://wanandroid.com/lg/todo/list/1/json
    http://wanandroid.com/lg/todo/list/2/json
    http://wanandroid.com/lg/todo/list/3/json

返回数据格式：

{
    "data": {
        "doneList": [
            {
                "date": 1532793600000,
                "todoList": [
                    {
                        "completeDate": 1533052800000,
                        "completeDateStr": "2018-08-01",
                        "content": "这里可以记录笔记，备忘信息等。",
                        "date": 1532793600000,
                        "dateStr": "2018-07-29",
                        "id": 82,
                        "status": 1,
                        "title": "已经完成的事情",
                        "type": 0,
                        "userId": 2
                    }
                ]
            }
        ],
        "todoList": [
            {
                "date": 1532016000000,
                "todoList": [
                    {
                        "completeDate": null,
                        "completeDateStr": "",
                        "content": "",
                        "date": 1532016000000,
                        "dateStr": "2018-07-20",
                        "id": 73,
                        "status": 0,
                        "title": "第一件未完成的事情",
                        "type": 0,
                        "userId": 2
                    }
                ]
            },
            {
                "date": 1532448000000,
                "todoList": [
                    {
                        "completeDate": null,
                        "completeDateStr": "",
                        "content": "",
                        "date": 1532448000000,
                        "dateStr": "2018-07-25",
                        "id": 80,
                        "status": 0,
                        "title": "第二件未完成的事情",
                        "type": 0,
                        "userId": 2
                    }
                ]
            }
        ],
        "type": 0
    },
    "errorCode": 0,
    "errorMsg": ""
}

    type 为类型 type取值为0，1，2，3
    doneList 为已经完成的todo，为列表格式，内部为 时间->List<Todo>这样的格式。
    todoList为未完成的todo，格式如上。

4.新增一条TODO
http://www.wanandroid.com/lg/todo/add/json

方法：POST
参数：
	title: 新增标题
	content: 新增详情
	date: 2018-08-01
	type: 0

5.更新一条TODO内容
http://www.wanandroid.com/lg/todo/update/83/json

方法：POST
参数：
	id: 拼接在链接上，为唯一标识
	title: 更新标题
	content: 新增详情
	date: 2018-08-01
	status: 0 // 0为未完成，1为完成
	type: 0

6.删除一条TODO
http://www.wanandroid.com/lg/todo/delete/83/json

方法：POST
参数：
	id: 拼接在链接上，为唯一标识

7.仅更新完成状态的TODO
http://www.wanandroid.com/lg/todo/done/80/json

方法：POST
参数：
	id: 拼接在链接上，为唯一标识
	status: 0或1，传1代表未完成到已完成，反之则反之。

8.未完成的TODO列表
http://www.wanandroid.com/lg/todo/listnotdo/0/json/1

http://www.wanandroid.com/lg/todo/listnotdo/类型/json/页码

方法：POST
参数：
	类型：类型拼接在链接上，目前支持0,1,2,3
	页码: 拼接在链接上，从1开始；

9.已完成TODO列表
http://www.wanandroid.com/lg/todo/listdone/0/json/1

http://www.wanandroid.com/lg/todo/listdone/类型/json/页码

方法：POST
参数：
	类型：类型拼接在链接上，目前支持0,1,2,3
	页码: 拼接在链接上，从1开始；