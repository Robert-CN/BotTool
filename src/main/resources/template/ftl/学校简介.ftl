<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">
        body {
            text-align: center;
            font-family: SimSun;
        }

        td {
            border: 1px solid;
            padding: 10px;
            background-color: #fff;
            min-width: 2em;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid;
        }

        @page {
            size: 11.5in 11in;
            @bottom-center {
                content: "page " counter(page) " of  " counter(pages);
            }
        }
        .big-box {
            text-align: center;
            padding: 25px;
        }

        .big-title {
            font-size: 30px;
            font-weight: 700;
        }

        .review-unit {
            font-size: 20px;
            text-align: left;
            font-weight: 700;
        }

        .center {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            margin: 0;
        }

        .beStrong {
            font-weight: 700;
        }

        /*模拟对角线*/
        .out {
            position: relative;
            background: url('line.jpg') center no-repeat;
            background-size: 100% 100%;
        }

        .out div {
            position: relative;
            z-index: 2;
        }

        .out div span:first-child {
            position: absolute;
            left: 10px;
            bottom: -15px;
        }

        .out div span:last-child {
            position: absolute;
            right: 10px;
            top: -15px;
        }
    </style>
</head>
<body>
<div class="big-box">
    <p class="big-title" style="margin-bottom: 20px;">学校简介</p>
    <p class="review-unit" style="margin-bottom: 15px;">编号：${number!} </p>
    <table>
        <tbody>
        <tr valign="bottom" style="height:19px;text-align: center;">
            <td valign="middle"  style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;">
                <span style="font-family:SimSun">学校名称</span>
            </td>
            <td valign="middle" colspan="4"
                style="border-right-width: 1px; border-right-color: black; border-bottom-width: 1px; border-bottom-color: black;">${schoolName!}
            </td>
        </tr>
        <tr valign="bottom" style="height:100px;text-align: center;">
            <td valign="middle"
                style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;">
                <span style="font-family:SimSun">校长</span>
            </td>
            <td valign="middle"
                style="border-right-width: 1px; border-right-color: black; border-bottom-width: 1px; border-bottom-color: black;">${xz!}
            </td>
            <td valign="middle"  style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;">
                <span style="font-family:SimSun">建立时间</span>
            </td>
            <td valign="middle" colspan="2"
                style="border-right-width: 1px; border-right-color: black; border-bottom-width: 1px; border-bottom-color: black;">${jlsj!}
            </td>
        </tr>

        <tr valign="bottom" style="height:200px;text-align: center;">
            <td valign="middle" style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;">
                <span style="font-family:SimSun">简介</span>
            </td>
            <td valign="middle" colspan="4"
                style="border-right-width: 1px; border-right-color: black; border-bottom-width: 1px; border-bottom-color: black;">${description!}
            </td>
        </tr>
        <tr valign="bottom" style="height:20px;text-align: center;">
            <td valign="middle" style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;">
                <span style="font-family:SimSun">占地面积(平方米) </span>
            </td>
            <td valign="middle" colspan="4"
                style="border-right-width: 1px; border-right-color: black; border-bottom-width: 1px; border-bottom-color: black;">${zdmj!}
            </td>
        </tr>
        <tr valign="bottom" style="height:19px;text-align: center;">
            <td valign="middle" colspan="5" style="border-bottom-width: 1px; border-bottom-color: black; border-top: none;height: 150px;">
                <span style="font-family:SimSun">发证机关（盖章）</span>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>