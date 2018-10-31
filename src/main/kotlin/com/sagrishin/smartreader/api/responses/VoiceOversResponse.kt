package com.sagrishin.smartreader.api.responses

import com.sagrishin.smartreader.models.repositories.models.VoiceOvers

data class VoiceOversResponse(val voiceOvers: VoiceOvers,
                              val status: Int)