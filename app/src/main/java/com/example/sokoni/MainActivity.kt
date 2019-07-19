package com.example.sokoni

import com.example.sokoni.Storage.repository.PrefrenceManager
import com.example.sokoni.Storage.repository.SokoniDatabase
import com.example.sokoni.models.oauth.Cart.Cart


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var qBadgeView: BadgeView
    lateinit var viewModel: MainViewModel
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                popOutFragments()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(9))
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_categories -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CategoriesFragment.newInstance()).addToBackStack("Cat").commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, FavoritesFragment.newInstance()).addToBackStack("Fav").commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance(9)).commitNow()
        qBadgeView = BadgeView(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        nav_view.setNavigationItemSelectedListener(this)
        setUpUI()
        cart.setOnClickListener { startActivity(Intent(this@MainActivity, Cart::class.java)) }
        search.setOnClickListener { startActivity(Intent(this@MainActivity, ProductSearch::class.java)) }


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // popOutFragments()
        when (item.itemId) {
            R.id.nav_orders -> {

            }
            R.id.nav_account -> {

            }
            R.id.nav_log_out -> {
                PrefrenceManager(this).setLoginStatus(0)
                val mDatabase = SokoniDatabase.getDatabase(this@MainActivity)
                mDatabase?.clearAllTables()
                finish()
                startActivity(Intent(this@MainActivity, SplashScreenActivity::class.java))
            }
            R.id.nav_settings -> {
                // startActivity(Intent(this@MainActivity, Categories::class.java))
            }
            R.id.nav_share -> {
                try {
                    val `in` = Intent()
                    `in`.action = Intent.ACTION_SEND
                    `in`.putExtra(Intent.EXTRA_TEXT, "Download Sokoni now and get 500 Kshs Voucher\n https://play.google.com/store/apps/details?id=com.kogicodes.sokoni")

                    `in`.type = "text/plain"
                    startActivity(`in`)
                } catch (nm: Exception) {
                }
            }
            R.id.nav_about -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun setUpUI() {
        viewModel.getOuthProfile().observe(this, Observer {
            try {
                txt_name.text = "" + it.firstName + " " + it.lastName
                txt_email.text = "" + it.email
            } catch (e: Exception) {
            }
        })
    }

    private fun setCartCount(count: Int?) {
        qBadgeView.bindTarget(cart).badgeText = "" + count
    }

    override fun onStart() {
        super.onStart()
        refreshBadge()
    }

    override fun onPause() {
        super.onPause()
        refreshBadge()
    }

    override fun onResume() {
        super.onResume()
        refreshBadge()
    }

    override fun onRestart() {
        super.onRestart()
        refreshBadge()
    }

    fun refreshBadge() {
        setCartCount(0)
        viewModel.getCartCount().observe(this, Observer {
            if (it != null)
                setCartCount(it.items_quantity)
        })
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        if (f is MainFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are You Sure?")

            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                super.onBackPressed()
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            val alert = builder.create()
            alert.show()            //additional code
        } else {
            popOutFragments()
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    fun setFavSelectedNav() {
        refreshBadge()

        if (navigation.selectedItemId != R.id.navigation_favorites) {
            navigation.selectedItemId = R.id.navigation_favorites
        }
    }

    fun setCategoriesSelectedNav() {
        if (navigation.selectedItemId != R.id.navigation_categories) {
            navigation.selectedItemId = R.id.navigation_categories
        }
    }

    fun setHomeSelectedNav() {
        refreshBadge()

        if (navigation.selectedItemId != R.id.navigation_home) {
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    internal fun popOutFragments() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
}