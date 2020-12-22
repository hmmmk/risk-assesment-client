package com.example.riskassesmentclient

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.riskassesmentclient.api.RiskAutomationApiService
import com.example.riskassesmentclient.api.models.Company
import com.example.riskassesmentclient.utils.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Nick Vasko on 22.12.2020
 * https://github.com/hmmmk
 */
class MainActivity : AppCompatActivity() {

    private lateinit var riskAutomationApiService: RiskAutomationApiService

    private var companies: List<Company>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        riskAutomationApiService = RetrofitUtils.getInstance(applicationContext)
            .createService(RiskAutomationApiService::class.java)

        initViews()
        initListeners()
    }

    private fun initViews() {
        launchUI {
            asyncR {
                riskAutomationApiService.getCompany(0).bodyOrError()
            }.awaitFold({
                companies = it

                initCompanies()
            }, {
                Toast.makeText(this@MainActivity, "Something went wrong.", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }

    private fun initListeners() {
        companiesTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setCompanyFields(companies!![tab.position])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun initCompanies() {
        companies?.forEachIndexed { index, company ->
            companiesTl.addTab(companiesTl.newTab().also {
                it.text = company.name
            })

            if (index == 0) {
                setCompanyFields(company)
            }
        }
    }

    private fun setCompanyFields(company: Company) {
        companyNameEt.setText(company.name)
        owcEt.setText(company.ownWorkingCapital.toString())
        ownCapitalEt.setText(company.ownCapital.toString())
        stAssetsEt.setText(company.stAssets.toString())
        stObligationsEt.setText(company.stObligations.toString())
        stNetProfitEt.setText(company.stNetProfit.toString())
        assetsEt.setText(company.assets.toString())
        obligationsEt.setText(company.obligation.toString())
        revenueEt.setText(company.revenue.toString())
    }
}