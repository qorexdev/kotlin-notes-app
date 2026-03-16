package com.qorexdev.notesapp.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.qorexdev.notesapp.R
import com.qorexdev.notesapp.data.model.Note
import com.qorexdev.notesapp.databinding.FragmentEditorBinding

class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by viewModels()
    private val args: EditorFragmentArgs by navArgs()

    private val colorViews = mutableListOf<ImageView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupColorPicker()
        setupMenu()
        observeViewModel()
        viewModel.loadNote(args.noteId)
    }

    private fun setupColorPicker() {
        val colorData = listOf(
            binding.colorDefault to Note.COLOR_DEFAULT,
            binding.colorRed to Note.COLOR_RED,
            binding.colorOrange to Note.COLOR_ORANGE,
            binding.colorYellow to Note.COLOR_YELLOW,
            binding.colorGreen to Note.COLOR_GREEN,
            binding.colorBlue to Note.COLOR_BLUE,
            binding.colorPurple to Note.COLOR_PURPLE
        )

        colorData.forEach { (imageView, colorId) ->
            colorViews.add(imageView)
            imageView.setOnClickListener {
                viewModel.selectedColor = colorId
                updateColorSelection(colorId)
            }
        }

        updateColorSelection(Note.COLOR_DEFAULT)
    }

    private fun updateColorSelection(selectedColor: Int) {
        val colorMap = mapOf(
            Note.COLOR_DEFAULT to 0,
            Note.COLOR_RED to 1,
            Note.COLOR_ORANGE to 2,
            Note.COLOR_YELLOW to 3,
            Note.COLOR_GREEN to 4,
            Note.COLOR_BLUE to 5,
            Note.COLOR_PURPLE to 6
        )

        colorViews.forEachIndexed { index, imageView ->
            val isSelected = colorMap[selectedColor] == index
            imageView.setImageResource(
                if (isSelected) R.drawable.ic_check else 0
            )
            imageView.alpha = if (isSelected) 1f else 0.6f
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.editor_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_pin -> {
                        viewModel.isPinned = !viewModel.isPinned
                        menuItem.setIcon(
                            if (viewModel.isPinned) R.drawable.ic_pin_filled
                            else R.drawable.ic_pin_outline
                        )
                        true
                    }
                    R.id.action_delete -> {
                        if (args.noteId != -1L) {
                            viewModel.deleteNote(args.noteId)
                        } else {
                            findNavController().navigateUp()
                        }
                        true
                    }
                    R.id.action_save -> {
                        saveNote()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeViewModel() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            note?.let {
                binding.editTitle.setText(it.title)
                binding.editContent.setText(it.content)
                updateColorSelection(it.color)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                findNavController().navigateUp()
            }
        }
    }

    private fun saveNote() {
        val title = binding.editTitle.text.toString().trim()
        val content = binding.editContent.text.toString().trim()
        viewModel.saveNote(args.noteId, title, content)
    }

    override fun onPause() {
        super.onPause()
        if (_binding != null) {
            saveNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
