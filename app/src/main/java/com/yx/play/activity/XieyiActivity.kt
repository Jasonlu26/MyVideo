package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sccdwxxyljx.com.databinding.ActivityXieyiBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click

/**
 * @description
 * @version
 */
class XieyiActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityXieyiBinding

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, XieyiActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityXieyiBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }

    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.tvContent.text = "1.信息收集\n" +
                "\n" +
                "    我们收集的数据取决于您与我们互动的环境，您做出的选择（包括您的隐私设置）以及您使用的产品和功能。我们收集的数据可以包括SDK / API / JS代码版本，浏览器，Internet服务提供商，IP地址，平台，时间戳，应用程序标识符，应用程序版本，应用程序分发渠道，独立的设备标识符，iOS广告标识符（IDFA），Android广告主标识符，网卡（MAC）地址和国际移动设备识别码（IMEI）设备型号，终端制造商，终端设备操作系统版本，会话开始/停止时间，语言的位置，时区以及网络状态（WiFi等），硬盘，CPU和电池使用情况等。\n" +
                "\n" +
                "2.个人资料的使用\n" +
                "\n" +
                "    为您提供服务" +
                " 产品改进。我们使用数据来不断改进我们的产品，包括添加新功能。例如。" +
                " 评估并改善我们的广告以及其他促销和促销活动。\n" +
                "\n" +
                "    邀请您参加我们服务的调查。\n" +
                " 为了使您获得更好的体验，改善我们的服务或您同意的其他用途，我们可能会根据相关法律法规将从某些服务收集的信息用于我们的其他服务。例如，您将使用您使用我们服务的信息向您显示个性化内容或其他服务中的广告，以进行用户研究分析和统计。\n" +
                " 为了确保服务的安全性并帮助我们更好地了解应用程序的操作，我们可能会记录相关信息，例如应用程序的频率，故障信息，总体使用情况，性能数据以及应用程序的来源。 。我们不会将存储在分析软件中的信息与您在应用程序中提供的个人身份信息相关联。\n" +
                "  \n" +
                "3.分享个人资料\n" +
                "  \n" +
                "    为了改进我们的产品并提供更好的服务，我们还与代表我们工作的供应商或代理商共享个人数据，以达到本隐私政策中所述的目的。例如，我们雇用来提供数据分析服务的公司可能需要收集和访问个人数据才能提供这些功能。在这种情况下，这些公司必须遵守我们的数据隐私和安全要求。\n" +
                "  \n" +
                "4. LGGAL数据处理基础\n" +
                "  \n" +
                "    如上所述，我们出于本隐私政策中规定的目的处理个人数据。我们处理个人数据的法律依据包括\n" +
                " 那是：与您签订合同所必需的（例如，向您提供您所请求的服务以及识别和认证您，以便您可以使用网站）；遵守法律要求的必要条件（例如，遵守适用的会计规则并向执法部门强制披露）；为了我们的合法利益（例如，管理我们与您的关系，确保我们的服务的安全性，与您就我们的产品和服务进行沟通）所必需；并获得我们客户的同意（例如，放置某些Cookie并出于广告目的与第三方共享您的信息）。\n" +
                "   \n" +
                " 在某些情况下，您可能需要向我们提供如上所述的个人数据进行处理，以便我们能够为您提供所有服务，并让您使用我们网站的所有功能。\n" +
                "  \n" +
                "5.个人数据的国际转移\n" +
                "  \n" +
                "    我们的业务可能会要求我们将您的个人数据转移到欧洲经济区（“ EEA”）以外的国家，包括中国或新加坡等国家。我们会采取适当的步骤来确保您的个人数据的接收者有保密义务，并且我们会执行标准合同条款等措施。可以通过与我们的帮助中心联系来获取这些条款的副本。\n" +
                "   \n" +
                "6.您的权利\n" +
                "  \n" +
                "    在适用法律的限制下，您有权反对或要求限制对您的个人数据的处理，并有权要求访问，更正，擦除和移植您自己的个人数据。\n" +
                " 如果您的信息使用是基于同意的，则您可以随时撤回此同意，而不会影响在撤回同意之前基于同意进行处理的合法性。\n" +
                "  \n" +
                " 可以通过[Android]与我们联系来提交请求。\n" +
                "  \n" +
                " 如果您知道自己信息的更改或不正确，则应向您告知此类更改，以便我们的记录可以更新或更正。如果您认为我们对您的个人数据的处理违反了适用法律，则可以向监管机构投诉。\n" +
                "  \n" +
                " 只要您需要向您提供服务或产品，或者根据要求或允许，我们将保留您的个人数据\n"

    }

    private fun fetchData() {

    }


}