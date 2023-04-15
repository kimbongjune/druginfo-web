<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>개인정보 취급방침 페이지 입니다</title>

<!-- Google Font: Source Sans Pro -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<!-- Font Awesome Icons -->
<link rel="stylesheet" href="/resources/css/all.min.css">
<!-- IonIcons -->

<link rel="stylesheet" href="/resources/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="/resources/css/adminlte.min.css">
</head>

<body class="sidebar-collapse">
<!-- jQuery -->
<script src='/resources/js/jquery.min.js'></script>
<!-- Bootstrap 4 -->
<script src='/resources/js/bootstrap.bundle.min.js'></script>
<div class="wrapper">
    
<style>
	.agreements-navbar{
	    justify-content: center;
	}
	
	.agreements-navlink{
		flex : 1;
	}
	
	.agreements-navbar-right{
		flex : 1;
	}
	
	.agreements-navbar-center{
	    font-weight: bold;
	}
	
	.agreements_header{
		margin-top : 3%;
		margin-left : 1%;
		margin-right : 1%;
	}
	.content-header h1{
		font-weight: bold;
	}
	.agreements-col-sm-6{
		flex : 0 0 100%;
		max-width : 100%;
	}
</style>
<nav class="main-header navbar navbar-expand navbar-white navbar-light agreements-navbar">
	<a class="nav-links agreements-navlink" data-widget="goBack" href="javascript:history.back();" role="button">
      <i class="fas fa-arrow-left"></i>
    </a>
    <div class="agreements-navbar-center">개인정보 취급방침</div>
    <div class="agreements-navbar-right"></div>
</nav>
    
<div class="content-wrapper"> 
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6 agreements-col-sm-6">
                <h1>이약모야 개인정보 취급방침</h1>
                <h6 class="agreements_header"> nocdev(이하 "개발자" 라 함)은 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립 공개합니다.</h6>
            </div>
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <!--<li class="breadcrumb-item"><a href="/admin/com/control.do">Home</a></li>  -->
                    <!--<li class="breadcrumb-item active">BrainSaver 개인정보 처리방침</li>-->
                </ol>
            </div>
        </div>
        </div><!-- /.container-fluid -->
    </section>  
    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-12" id="accordion">
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseOne">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                       			제1조(개인정보의 처리 목적)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseOne" class="collapse show" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은 개인정보를 수집하지 않는 애플리케이션으로 사용자에게 별도의 정보를 수집하지 않습니다.<br> 
							이약모야 앱에서는 개인정보를 저장하거나 이용하지 않습니다.<br>
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseTwo">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	 제2조(개인정보의 처리 및 보유 기간)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseTwo" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                       		이약모야 앱은 개인정보를 수집하지 않으며 저장하지 않습니다.<br>
						   	즉, 개인정보를 처리 및 보유기간이 존재하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseThree">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                           		제3조(처리하는 개인정보의 항목)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseThree" class="collapse" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은 개인정보를 수집하지 않습니다.<br>
							즉, 처리하는 개인정보 항목이 존재하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseFour">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제4조(개인정보의 제3자 제공에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseFour" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						   이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						   즉, 개인정보를 제3자에게 제공하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseFive">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제5조(개인정보처리의 위탁에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseFive" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						   	이약모야 앱은 개인정보를 수집하지 않습니다.<br>
					  		즉, 개인정보를 위탁하지 않습니다.
                        </div>
                    </div>
                </div>
				<div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseSix">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                           		제6조(개인정보의 파기절차 및 파기방법)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseSix" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						  즉, 파기 할 개인정보가 존재하지 않습니다.<br><br>
						  <b>그러나 사용자가 원할 경우 이약모야 앱을 삭제함으로서 저장된 모든 데이터를 파기할 수 있습니다.</b>
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseSeven">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                           		제7조(정보주체와 법정대리인의 권리·의무 및 그 행사방법에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseSeven" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						  즉, 개인정보 열람·정정·삭제·처리정지 등의 요구에 대한 처리를 할 수 없습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseEight">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제8조(개인정보의 안전성 확보조치에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseEight" class="collapse" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은 개인정보를 수집하지 않습니다.<br>
							즉, 개인정보의 안전한 처리를 위해 별도의 조치를 하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseNine">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제9조(개인정보를 자동으로 수집하는 장치의 설치·운영 및 그 거부에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseNine" class="collapse" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseTen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제10조(행태정보의 수집·이용·제공 및 거부 등에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseTen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은(는) 온라인 맞춤형 광고 등을 위한 행태정보를 수집·이용·제공하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseEleven">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                          		제11조(추가적인 이용·제공 판단기준)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseEleven" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						  즉, 동의없이 개인정보를 추가적으로 이용, 제공하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseTwelve">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제12조(가명정보를 처리하는 경우 가명정보 처리에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseTwelve" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						   이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						   즉, 가명정보에 대한 처리를 하지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseThirteen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제13조 (개인정보 보호책임자에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseThirteen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						  즉, 개인정보 보호책임자가 존재하지 않습니다.<br><br>
						 <b>하지만 정보주체가 원한다면 개발자에게 연락 및 문의가 가능합니다.</b><br><br>
						 &nbsp;&nbsp;-성명 : 김봉준<br>
						  &nbsp;&nbsp;-직책 : 개발자<br>
						  &nbsp;&nbsp;-이메일 : nocdu112@naver.com<br>
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseFourteen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제14조(국내대리인의 지정)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseFourteen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
							이약모야 앱은 개인정보를 수집하지 않습니다.<br>
							즉, 국내 대리인이 존재하지 않습니다.
                        </div>
                    </div>
                </div>
				<div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseFifteen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제15조(개인정보의 열람청구를 접수·처리하는 부서)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseFifteen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 개인정보를 수집하지 않습니다.<br>
						  즉, 개인정보의 열람청구를 접수, 처리하는 부서가 존재하지 않습니다.<br><br>
						 <b>하지만 정보주체가 원한다면 개발자에게 연락 및 문의가 가능합니다.</b><br><br>
						 &nbsp;&nbsp;-성명 : 김봉준<br>
						  &nbsp;&nbsp;-직책 : 개발자<br>
						  &nbsp;&nbsp;-이메일 : nocdu112@naver.com<br>
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseSixteen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제16조(정보주체의 권익침해에 대한 구제방법)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseSixteen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.<br>
						  <blockquote>
						  &nbsp;&nbsp;- 1. 개인정보분쟁조정위원회 : (국번없이) 1833-6972 (www.kopico.go.kr)<br>
						  &nbsp;&nbsp;- 2. 개인정보침해신고센터 : (국번없이) 118 (privacy.kisa.or.kr)<br>
						  &nbsp;&nbsp;- 3. 대검찰청 : (국번없이) 1301 (www.spo.go.kr)<br>
						  &nbsp;&nbsp;- 4. 경찰청 : (국번없이) 182 (ecrm.cyber.go.kr)<br>
						  </blockquote>
						  「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대 하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br><br>
						  ※ 행정심판에 대해 자세한 사항은 중앙행정심판위원회(www.simpan.go.kr) 홈페이지를 참고하시기 바랍니다.<br>
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseSeventeen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제17조(영상정보처리기기 운영·관리에 관한 사항)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseSeventeen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이약모야 앱은 상정보처리기기를 설치, 운영하고 있지 않습니다.
                        </div>
                    </div>
                </div>
                <div class="card card-primary card-outline">
                    <a class="d-block w-100" data-toggle="collapse" href="#collapseEighteen">
                        <div class="card-header">
                            <h4 class="card-title w-100">
                               	제18조(개인정보 처리방침 변경)
                            </h4>
                        </div>
                    </a>
                    <div id="collapseEighteen" class="collapse" data-parent="#accordion">
                        <div class="card-body">
						  이 개인정보처리방침은 2023년 5월 1부터 적용됩니다
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12 mt-3 text-center">
                <p class="lead">
                </p>
            </div>
        </div>
    </section>
    <!-- /.content -->
<!-- /.content-wrapper -->
</div>
    

<!-- Main Footer -->
<footer class="main-footer">
    
    <div class="float-right d-none d-sm-block">
        <b>이약모야</b>
    </div>
    
    <strong>Copyright &copy; 2023 <a href="#">이약모야</a>.</strong>
    All rights reserved.

</footer>
<!-- /.Main Footer -->
    

</div>
</body>
</html>