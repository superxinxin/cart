# cart
## 购物车项目
该项目使用Servlet结合JSP开发购物车功能，主要从以下几个方面介绍该项目：（1）类关系，（2）产品模块，（3）用户模块，（4）购物车模块，（5）订单模块
### （1）类关系：Product 产品，User 用户，Order 订单，OrderItem 订单项。一条记录就是一个订单项，对应一种商品，以及购买数量。
	1. 产品和订单项的关系是 一对多
		一种产品，对应多条订单项。
		一条订单项，对应一种产品
	2. 订单项和订单的关系 多对一
		一个订单里有多条订单项
		一个订单项，只会出现在一个订单里
	3. 订单和用户的关系： 多对一
		一个订单，只能属于一个用户
		一个用户，可以下多个订单 
### （2）产品模块：
	1. 在本地MySQL数据库中新建名为cart的数据库，新建名为product的表并赋初始值。
	2. 在bean包中新建Product类。
	3. 在dao包中新建ProductDao类提供对Product的查询。
	4. 在servlet包中新建ProductListServlet类，作用是通过ProductDAO把product从数据库查出来，然后通过listProduct.jsp显示出来。
	5. 新建listProduct.jsp，显示产品名称，价格，并为每种商品提供一个购买按钮，提交到“addOrderItem”。这里使用JQuery实现，每次加入商品成功后页面显示“加入购物车成功”，且页面不跳转。
	6. 配置web.xml，在web.xml中为路径/listProduct加上相关配置。
### （3）用户模块：
	1. 在cart的数据库里，新建名为user的表并赋初始值。
	2. 在bean包中新建User类。
	3. 在dao包中新建UserDao类，根据name和password查询表user,如果有数据就表示账号密码正确。
	4. 新建login.jsp，用户登录界面，提交到“login”。
	5. 在servlet包中新建UserLoginServlet，登录Servlet， 通过name和password获取user对象，如果对象不为空，就表示账号密码正确，跳转到产品显示界面/listProduct，如果对象为空，即数据库中没有该用户，就跳转到登录界面，重新登录。
	6. 配置web.xml，在web.xml中为路径/login加上相关配置。
	7. 修改listProduct.jsp，如果用户登陆了，就显示用户的名字。
### （4）购物车模块：
	1. 在bean包中新建OrderItem类，该类有Product类型的product，没有使用int类型的pid，因为在后续显示购物车的时候，可以很简单的通过el表达式就显示商品名称和价格。
	2. 在dao包中新建UserDao类，因为购买的时候，提交到服务器的是pid, 而OrderItem类的product属性是Product类型的，所以ProductDAO需要根据id获取Product对象。
	3. 在servlet包中新建OrderItemAddServlet类。购买行为本身就是创建一个OrderItem对象，在负责购买商品的OrderItemAddServlet中，进行如下流程:
		1. 获取购买数量
		2. 获取购买商品的id
		3. 根据id获取商品对象
		4. 创建一个新的OrderItem对象
		5. 从session中取出一个List , 这个List里面存放陆续购买的商品。如果是第一次从session中获取该List,那么它会是空的，需要创建一个ArrayList
		6. 把新创建的OrderItem对象放入该List中
		7. 跳转到显示购物车的listOrderItem 
	4. 在servlet包中新建OrderItemListServlet，显示购物车的OrderItemListServlet其实什么也没做，因为数据已经在session准备好了，直接服务端跳转到listOrderItem.jsp，在listOrderItem.jsp中，从session中遍历出所有的OrderItem。因为保存在OrderItem上的是一个Product对象，所以很容易就可以通过EL表达式遍历出商品的名称和价格。
	5. 在listOrderItem.jsp中，在每条OrderItem后增加一个删除选项，提交到“deleteOrderItem”，实现对该订单项的删除，在对应的OrderItemDeleteServlet中删除提交的订单项，再在listOrderItem.jsp里呈现删除后的所有订单项。
	6. 为了应对购买了同一商品情况的出现，需要修改OrderItemAddServlet，遍历session中所有的OrderItem，如果找到对应的product.id一样的条目，就调整其数量，如果没有找到，就新增加一条。 
	7. 配置web.xml，在web.xml中为路径/addOrderItem，/listOrderItem及/deleteOrderItem加上相关配置。
### （5）订单模块：
	1. 在cart数据库中，创建order表，里面有一个uid字段用于表明该订单属于哪个用户。
	2. 创建orderitem表，有id,pid,num,oid等字段。分别表示主键，商品对应的id，购买数量以及订单id。
 	3. 在bean包中新建Order类，与OrderItem类似的，会有一个User属性，而不是使用int类型的uid。
	4. OrderItem在原来的基础上，增加一个Order属性，表示该订单项属于哪一个订单。
	5. 在dao包中新建OrderDAO，OrderDAO把订单对象保存到数据库中。Order对象保存到数据库中后，该对象就会有对应的id，这个id，在后续保存OrderItem的时候，是作为order id存在的。所以在保存的数据库的时候，要获取自增长id值。
	6. 在dao包中新建OrderItemDAO，将OrderItem保存到数据库中。
	7. 新建listOrderItem.jsp，在listOrderItem.jsp页面新增加一个"生成订单"的链接。
	8. 在servlet包中新建OrderCreateServlet，主要作用如下：
		1. 首选判断用户是否登陆，如果没有登陆跳转到登陆页面
		2. 创建一个订单对象，并设置其所属用户
		3. 把该订单对象保存到数据库中
		4. 遍历session中所有的订单项，设置他们的Order。然后保存到数据库中
		5. 清空session中的订单项
		6. 最后打印订单创建成功
	9. 配置web.xml，在web.xml中为路径/createOrder加上相关配置。
