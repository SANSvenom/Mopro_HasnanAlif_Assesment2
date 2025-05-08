package com.hasnan0062.assesment2.screen

import androidx.lifecycle.ViewModel
import com.hasnan0062.assesment2.model.Buku



class MainViewModel: ViewModel() {
    val data = listOf(
        Buku(
            1,
            "Fadhli Firdausi",
            "607062300117",
            "D3IF-47-01"
        ),
        Buku(
            2,
            "Albert Peter Paker",
            "607054111101",
            "D3IF-47-02"
        ),
        Buku(
            3,
            "Ken Situmorang",
            "607054111201",
            "D3IF-47-06"
        ),
        Buku(
            4,
            "Rima Hayati",
            "607054111133",
            "D3IF-47-03"
        ),
        Buku(
            5,
            "Nana",
            "607054111124",
            "D3IF-47-06"
        ),

        Buku(
            6,
            "Alkatiri Abdullah",
            "607054111890",
            "D3IF-47-04"
        ),
        Buku(
            7,
            "Karin Susanti",
            "607062400118",
            "D3IF-47-05"
        ),
        Buku(
            8,
            "Chila Oxford",
            "607062400110",
            "D3IF-47-05"
        ),
        Buku(
            9,
            "Apip Maulana",
            "607062400111",
            "D3IF-47-05"
        ),
        Buku(
            10,
            "Mentari Arunika Ayu",
            "607062400119",
            "D3IF-47-05"
        ),


        )
}