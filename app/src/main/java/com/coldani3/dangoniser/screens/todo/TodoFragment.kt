package com.coldani3.dangoniser.screens.todo

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.TodoData
import com.coldani3.dangoniser.data.bases.DBTodoListItem
import com.coldani3.dangoniser.databinding.FragmentTodoBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding;
    private lateinit var todoData: TodoData;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_todo, container, false)
        binding = DataBindingUtil.inflate<FragmentTodoBinding>(inflater,
            R.layout.fragment_todo,container,false);

        if (savedInstanceState != null) {
            todoData = savedInstanceState.getSerializable(TODO_ID) as TodoData;
        } else if (arguments != null) {
            todoData = arguments?.getSerializable(MainActivity.TODO_DATA_PASS_ID) as TodoData;
        } else {
            todoData = TodoData("Write title here");
        }

        binding.todoText.text = Editable.Factory.getInstance().newEditable(todoData.title);
        binding.todoDate.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(todoData.forDate));

        binding.doneButton.setOnClickListener { view -> updateDB(); };

        return binding.root;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TODO_ID, todoData);
    }

    private fun updateDB() {
        lifecycleScope.launch {
            val prevTodo: DBTodoListItem = MainActivity.database.get().todoListDao().getTodoByUID(todoData.uid);

            if (prevTodo != null) {
                todoData.updateDB();
            } else {
                Log.d(MainActivity.DEBUG_LOG_NAME, "Could not find matching event in database for ID: " + todoData.uid);
                MainActivity.database.get().todoListDao().insertTodo(TodoData.toDBObject(todoData));
            }

            findNavController().navigate(R.id.action_todoFragment_to_homeFragment);
        }
    }

    companion object {
        const val TODO_ID = "todoData"
    }
}