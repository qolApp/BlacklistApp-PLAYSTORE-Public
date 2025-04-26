package pe.com.gianbravo.blockedcontacts.presentation

import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.BlockedNumberContract
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.databinding.FragmentBlockedNumbersBinding
import pe.com.gianbravo.blockedcontacts.presentation.adapter.RvNumberListAdapter
import pe.com.gianbravo.blockedcontacts.presentation.common.base.BaseFragment
import pe.com.gianbravo.blockedcontacts.presentation.dialog.HowToUseDialogFragment
import pe.com.gianbravo.blockedcontacts.presentation.view.touchHelper.SimpleItemTouchHelperCallback
import pe.com.gianbravo.blockedcontacts.toast
import pe.com.gianbravo.blockedcontacts.utils.DialogUtil
import kotlin.coroutines.CoroutineContext

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class BlockedNumbersFragment : BaseFragment(), CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private lateinit var rvAdapter: RvNumberListAdapter
    private lateinit var callback: SimpleItemTouchHelperCallback
    private var _binding: FragmentBlockedNumbersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockedNumbersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObservers()
        registerListeners()
        setupViews()
    }

    private fun registerObservers() {}

    private fun registerListeners() {
        binding.buttonBlock.setOnClickListener {
            val number = binding.etNumber.text.toString()
            putNumberOnBlocked(
                number = number, context = context, onSuccess = {
                    binding.etNumber.setText("")
                    refreshList()
                }
            )
        }

        binding.buttonRemove.setOnClickListener {
            val number = binding.etNumber.text.toString()
            removeNumberFromBlocker(number)
            refreshList()
        }

        binding.buttonExport.setOnClickListener {
            findNavController().navigate(BlockedNumbersFragmentDirections.toFileDialog().apply { isImport = false })
        }

        binding.buttonImport.setOnClickListener {
            findNavController().navigate(BlockedNumbersFragmentDirections.toFileDialog().apply { isImport = true })
        }

    }

    private fun setupViews() {
        setupMenu()

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.app_name)

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.rvList.layoutManager = linearLayoutManager
        rvAdapter =
            RvNumberListAdapter(
                context,
                null,
                object :
                    RvNumberListAdapter.OnItemSelected {
                    override fun onItemSelected(
                        item: String?,
                        buttonView: CompoundButton?,
                        isChecked: Boolean
                    ) {
                        binding.etNumber.setText(item)
                    }
                },
                object :
                    RvNumberListAdapter.OnItemRemove {
                    override fun onItemRemove(item: String?, position: Int) {

                        DialogUtil.showDialogListener(context,
                            getString(R.string.text_remove_1) + " '$item' " + getString(R.string.text_remove_2),
                            true,
                            closeEnabled = true,
                            sendEnabled = true,
                            onEventDialog = object : DialogUtil.OnEventDialog {
                                override fun onClickSend() {
                                    removeNumberFromBlocker(item)
                                    rvAdapter.removeItem(position)
                                    binding.tvCount.text = rvAdapter.itemCount.toString()
                                }

                                override fun onCancel() {
                                    rvAdapter.notifyItemChanged(position)
                                }

                            })

                    }
                })
        binding.rvList.adapter = rvAdapter

        callback = SimpleItemTouchHelperCallback(rvAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvList)

        refreshList()
    }

    private fun setupMenu() {
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_blacklist, menu)
                menu.findItem(R.id.action_info).apply{
                    isVisible = true
                }
                menu.findItem(R.id.action_caller).apply{
                    isVisible = true
                }
                menu.findItem(R.id.action_search).apply{
                    isVisible = true
                    val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                    val searchView = this.actionView as SearchView
                    searchView.setOnLongClickListener { true }
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                    searchView.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            rvAdapter.filter.filter(newText)
                            return false
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_info -> {
                        showHowToDialog()
                        true
                    }
                    R.id.action_caller -> {
                        showChangeDefaultDialerSelector()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun refreshList() {
        binding.tvCount.text = rvAdapter.loadData(getBlocked(context)).toString()
    }



    private fun removeNumberFromBlocker(number: String?) {
        number?.let {
            val values = ContentValues()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number)
                val uri =
                    context?.contentResolver?.insert(
                        BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                        values
                    )
                uri?.let { it1 -> context?.contentResolver?.delete(it1, null, null) }
                context?.toast(getString(R.string.text_removed_number))
                binding.etNumber.setText("")
            }
        }
    }


    private fun showHowToDialog() {
        val dialogModifyEntriesFragment =
            HowToUseDialogFragment()
        val bundle = Bundle()
        dialogModifyEntriesFragment.arguments = bundle
        childFragmentManager.let {
            dialogModifyEntriesFragment.show(it, "aea")
        }
    }

    private fun showChangeDefaultDialerSelector() {
        if (activity is DialerActivity)
            (activity as DialerActivity).showChangeDefaultDialerSelector(true)
    }

    companion object {


        fun putNumberOnBlocked(
            number: String,
            isFromMultiple: Boolean = false,
            context: Context?,
            onSuccess: ()-> Unit
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val values = ContentValues()
                values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number)
                context?.contentResolver?.insert(
                    BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    values
                )
                if (!isFromMultiple) {
                    context?.toast(context.getString(R.string.text_added_number))
                    onSuccess()
                }
            } else
                context?.toast(context.getString(R.string.text_not_supported))
        }

        fun getBlocked(context: Context?): ArrayList<String> {
            val list = arrayListOf<String>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context?.contentResolver?.let {
                    val record: Cursor? =
                        it.query(
                            BlockedNumberContract.BlockedNumbers.CONTENT_URI, arrayOf(
                                BlockedNumberContract.BlockedNumbers.COLUMN_ID,
                                BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                                BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER
                            ), null, null, null
                        )

                    if (record != null && record.count != 0) {
                        if (record.moveToFirst()) {
                            do {
                                val blockNumber =
                                    record.getString(record.getColumnIndex(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER))
                                list.add(blockNumber)
                            } while (record.moveToNext())
                        }
                        record.close()
                    }
                }
            }
            list.sort()
            return list
        }
    }
}
