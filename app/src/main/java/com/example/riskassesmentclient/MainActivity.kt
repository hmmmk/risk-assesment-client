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

    private var companies = arrayListOf<Company>()

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
                companies = ArrayList(it)

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

        deleteBtn.setOnClickListener {
            if (!companies.isNullOrEmpty()) {
                deleteCompany(companies!![companiesTl.selectedTabPosition])
            } else {
                Toast.makeText(this@MainActivity, "Nothing to delete", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        createBtn.setOnClickListener {
            createCompany()
        }

        editBtn.setOnClickListener {
            if (!companies.isNullOrEmpty()) {
                editCompany()
            } else {
                Toast.makeText(this@MainActivity, "Nothing to edit", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        countBtn.setOnClickListener {
            if (!companies.isNullOrEmpty()) {

            } else {
                Toast.makeText(this@MainActivity, "Nothing to count", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initCompanies() {
        companies?.forEachIndexed { index, company ->
            companiesTl.addTab(companiesTl.newTab().also {
                it.text = company.name
                it.tag = company.name + company.id
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

    private fun deleteCompany(company: Company) {
        launchUI {
            asyncR {
                riskAutomationApiService.deleteCompany(company.id).isSuccessfulOrError()
            }.awaitFold({
                companies!!.remove(company)
                companiesTl.removeTabAt(companiesTl.selectedTabPosition)
            }, {
                Toast.makeText(this@MainActivity, "Something went wrong.", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }

    private fun editCompany() {
        val newCompany = Company(
            assetsEt.text.toString().toDouble(),
            companies[companiesTl.selectedTabPosition].id,
            companyNameEt.text.toString(),
            obligationsEt.text.toString().toDouble(),
            ownCapitalEt.text.toString().toDouble(),
            owcEt.text.toString().toDouble(),
            revenueEt.text.toString().toDouble(),
            stAssetsEt.text.toString().toDouble(),
            stNetProfitEt.text.toString().toDouble(),
            stObligationsEt.text.toString().toDouble(),
            companies!![companiesTl.selectedTabPosition].userId
        )

        launchUI {
            asyncR {
                riskAutomationApiService.createCompany(newCompany).bodyOrError()
            }.awaitFold({
                companies!!.removeAt(companiesTl.selectedTabPosition)
                companies!!.add(companiesTl.selectedTabPosition, newCompany)
            }, {
                Toast.makeText(this@MainActivity, "Something went wrong.", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }

    private fun createCompany() {
        val newCompany = Company(
            assetsEt.text.toString().toDouble(),
            0,
            companyNameEt.text.toString(),
            obligationsEt.text.toString().toDouble(),
            ownCapitalEt.text.toString().toDouble(),
            owcEt.text.toString().toDouble(),
            revenueEt.text.toString().toDouble(),
            stAssetsEt.text.toString().toDouble(),
            stNetProfitEt.text.toString().toDouble(),
            stObligationsEt.text.toString().toDouble(),
            0
        )

        launchUI {
            asyncR {
                riskAutomationApiService.createCompany(newCompany).bodyOrError()
            }.awaitFold({
                companies!!.add(newCompany)
                companiesTl.selectTab(companiesTl.getTabAt(companies!!.size - 1))
            }, {
                Toast.makeText(this@MainActivity, "Something went wrong.", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }
}