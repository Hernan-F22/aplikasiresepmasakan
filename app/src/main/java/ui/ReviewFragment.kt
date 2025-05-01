package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasiresepmasakan.databinding.FragmentReviewBinding


class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Contoh: aksi saat tombol kirim ditekan
        binding.btnKirim.setOnClickListener {
            val ulasan = binding.etUlasan.text.toString()
            if (ulasan.isNotEmpty()) {
                binding.tvHasil.text = "Ulasan kamu: $ulasan"
                binding.etUlasan.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}